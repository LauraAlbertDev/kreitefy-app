import {
  ChangeDetectorRef,
  Component,
  EventEmitter,
  inject,
  Input,
  OnChanges,
  OnInit,
  Output,
  signal,
  SimpleChanges
} from '@angular/core';
import { FormGroup, ReactiveFormsModule } from '@angular/forms';
import { BaseService } from '../../../../services/base-service';
import { FormField, PreviewConfig } from '../../../../shared/form-field';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastService } from '../../../../services/toast-service';
import { HttpErrorResponse } from '@angular/common/http';
import { ErrorMessagePipe } from '../../../../shared/pipes/error-message';
import { UpperCasePipe } from '@angular/common';
import { ApiErrorResponse, EntidadAnidada, ResolverMap } from '../../../../shared/generic';

@Component({
  selector: 'app-generic-edit',
  imports: [ReactiveFormsModule, ErrorMessagePipe, UpperCasePipe],
  templateUrl: './generic-edit.html',
  styleUrl: './generic-edit.scss',
})
export class GenericEdit<T extends Record<string, any>> implements OnInit, OnChanges {
  private readonly cdr = inject(ChangeDetectorRef);
  private readonly router = inject(Router);
  private readonly route = inject(ActivatedRoute);
  private readonly toastService = inject(ToastService);
  @Input({ required: true }) service!: BaseService<T>;
  @Input('id') id?: number | string;
  @Input({ required: true }) form!: FormGroup;
  @Input({ required: true }) fields: FormField[] = [];
  @Input({ required: true }) previewConfig!: PreviewConfig;

  @Input() resolvers?: ResolverMap<T>;
  @Output() submitForm = new EventEmitter<void>();
  private labelsMap: Map<string, string> = new Map();
  protected conflictMessage = signal<string>('');
  private hasLoadedData = false;
  protected savedBackendData: Partial<Record<keyof T, string | unknown>> | null = null;

  ngOnInit() {
    if (!this.id) {
      this.id = this.route.snapshot.params['id'] || this.route.parent?.snapshot.params['id'];
    }

    if (this.id && !this.hasLoadedData) {
      this.hasLoadedData = true;
      this.load(Number(this.id));
    }

    const navigation = this.router.getCurrentNavigation()?.extras.state || window.history.state;
    if (!this.id && navigation && navigation.data) {
      this.form.patchValue(navigation.data);
      this.form.get('id')?.setValue(null);
    }
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes['fields'] && this.fields && this.fields.length > 0) {
      this.labelsMap.clear();
      this.fields.forEach((f) => this.labelsMap.set(f.name, f.label));
      if (this.savedBackendData) this.form.patchValue(this.savedBackendData);
      this.cdr.markForCheck();
      this.cdr.detectChanges();
    }
  }

  protected getFieldConfig(fieldName: string): FormField | undefined {
    return this.fields.find((f) => f.name === fieldName);
  }

  protected getLabel(fieldName: string): string {
    return this.getFieldConfig(fieldName)?.label || fieldName;
  }

  onSubmit() {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const rawValue = { ...this.form.getRawValue() };

    if (this.resolvers) {
      Object.keys(this.resolvers).forEach((key) => {
        const transform = this.resolvers![key];
        const currentValue = rawValue[key];

        if (transform && currentValue !== undefined) {
          rawValue[key] = transform(currentValue);
        }
      });
    }

    const objectToSend: Partial<T> & { id?: number } = {
      ...rawValue,
      ...(this.id ? { id: Number(this.id) } : {})
    };

    if (!this.id) {
      delete objectToSend.id;
    }

    const handleResponse = {
      next: () => {
        this.toastService.show({
          title: 'Guardado',
          body: 'Disfruta, se ha guardado correctamente',
          classname: 'bg-success text-light',
          delay: 4000,
        });
        this.router.navigate(['/']);
      },
      error: (error: HttpErrorResponse) => {
        const apiError = error.error as ApiErrorResponse;
        const mensajeFinal = apiError?.message;
        this.toastService.show({
          title: 'Error',
          body: mensajeFinal || 'Conflicto de datos',
          classname: 'bg-danger text-light',
          delay: 5000
        });
        this.cdr.detectChanges();
      },
    };

    if (this.id) this.service.update(Number(this.id), objectToSend as T).subscribe(handleResponse);
    else this.service.create(objectToSend as T).subscribe(handleResponse);
  }

  private load(id: number) {
    this.service.findById(id).subscribe({
      next: (t: T) => {
        this.form.reset();
        const rawData = t as Record<string, unknown>;
        const flattenedData: Record<string, unknown> = { ...rawData };
        ['artista', 'estilo', 'album'].forEach(key => {
          const val = flattenedData[key];
          if (val && typeof val === 'object') {
            const ent = val as EntidadAnidada;
            flattenedData[key] = (ent.nombre ?? ent.titulo ?? ent)?.toString().trim();
          }
        });
        this.savedBackendData = flattenedData as Partial<Record<keyof T, string | unknown>>;
        this.form.patchValue(flattenedData);

        this.cdr.markForCheck();
        this.cdr.detectChanges();
      },
      error: (error) => {
        const apiError = error.error as ApiErrorResponse;
        const msg = apiError?.message || error?.message || 'Error desconocido al cargar';
        this.conflictMessage.set(msg);
      },
    });
  }

  protected isRequired(fieldName: string): boolean {
    const control = this.form.get(fieldName);
    if (!control || !control.validator) return false;
    const validator = control.validator({} as any);
    return validator && validator['required'];
  }

  onFileSelected(event: Event, fieldName: string) {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files[0]) {
      const file = input.files[0];
      const reader = new FileReader();
      reader.onload = () => {
        this.form.get(fieldName)?.setValue(reader.result);
        this.cdr.markForCheck();
        this.cdr.detectChanges();
      };
      reader.readAsDataURL(file);
    }
  }

  protected clearImageField(fieldName: string) {
    this.form.get(fieldName)?.setValue('');

    if (this.savedBackendData) this.savedBackendData[fieldName as keyof T] = '';
  }
}
