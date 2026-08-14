import { ChangeDetectorRef, Component, inject, OnInit } from '@angular/core';
import { AuthService } from '../../../services/auth/auth-service';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { UsuariosService } from '../../../services/crud/usuarios-service';
import { ToastService } from '../../../services/toast-service';
import { HttpErrorResponse } from '@angular/common/http';
import { matchPasswordsValidator } from '../../../../Validators/FormValidators';
import { ReproduccionService } from '../../../services/crud/reproduccion-service';
import { Reproduccion } from '../../../shared/interfaces';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-perfil',
  imports: [ReactiveFormsModule, RouterLink, DatePipe],
  templateUrl: './perfil.html',
  styleUrl: './perfil.scss',
})
export class Perfil implements OnInit {
  private fb: FormBuilder = inject(FormBuilder);
  private authService: AuthService = inject(AuthService);
  private userService: UsuariosService = inject(UsuariosService);
  private reproduccionService: ReproduccionService = inject(ReproduccionService);
  private readonly router: Router = inject(Router);
  private readonly toastService: ToastService = inject(ToastService);
  public reproducciones: Reproduccion[] = [];
  private cdr: ChangeDetectorRef = inject(ChangeDetectorRef);

  public perfilForm: FormGroup = this.fb.group(
    {
      nombre: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(100)]],
      apellidos: ['', [Validators.required, Validators.minLength(10), Validators.maxLength(100)]],
      email: [
        '',
        [
          Validators.required,
          Validators.pattern(/^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/),
          Validators.maxLength(255),
        ],
      ],
      password: ['', [Validators.minLength(10), Validators.maxLength(100)]],
      confirmPassword: [''],
      rol: [{ value: '', disabled: true }],
      username: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(100)]],
    },
    { validators: matchPasswordsValidator },
  );

  ngOnInit() {
    this.userService.getUsuario().subscribe({
      next: (user) => {
        const { password: _password, ...usuarioLimpio } = user;
        this.perfilForm.patchValue(usuarioLimpio);
        this.perfilForm.get('rol')?.setValue(user.rol);
        setTimeout(() => {
          this.perfilForm.get('password')?.setValue('');
          this.perfilForm.get('confirmPassword')?.setValue('');
        }, 50);
      },
    });

    this.reproduccionService.obtenerReproducciones().subscribe({
      next: (data) => {
        this.reproducciones = data;
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.log(err);
      },
    });
  }

  protected onSubmit() {
    console.log('submitting');
    if (this.perfilForm.invalid) {
      this.perfilForm.markAllAsTouched();
      return;
    }
    const rawValue = this.perfilForm.getRawValue();
    delete rawValue.confirmPassword;
    if (!rawValue.password || rawValue.password.trim() === '') rawValue.password = null;

    const rawValueWithId = {
      ...rawValue,
    };

    this.userService.update(rawValueWithId).subscribe({
      next: (res: any) => {
        this.toastService.show({
          title: 'Guardado',
          body: 'El perfil se ha actualizado correctamente',
          classname: 'bg-success text-light',
          delay: 4000,
        });
        this.authService.logout();
      },
      error: (error: HttpErrorResponse) => {
        let mensajeFinal = error.error?.message;
        this.toastService.show({
          title: 'Error',
          body: mensajeFinal || 'Conflicto de datos',
          classname: 'bg-danger text-light',
          delay: 5000,
        });
      },
    });
  }

  public fields = [
    { label: 'Usuario', name: 'username', type: 'text' },
    { label: 'Nombre', name: 'nombre', type: 'text' },
    { label: 'Apellidos', name: 'apellidos', type: 'text' },
    { label: 'Email', name: 'email', type: 'text' },
    {
      label: 'Password',
      name: 'password',
      type: 'password',
      placeholder: 'Dejar en blanco para no cambiar',
    },
    {
      label: 'Confirmar Password',
      name: 'confirmPassword',
      type: 'password',
      placeholder: 'Dejar en blanco para no cambiar',
    },
  ];

  public obtenerMensajeError(controlName: string, label: string): string {
    const control = this.perfilForm.get(controlName);
    if (!control || !control.errors) return '';

    if (control.hasError('required')) return `El campo ${label.toLowerCase()} es obligatorio.`;
    if (control.hasError('pattern')) return 'El formato del correo no es válido.';

    const minLengthError = control.getError('minlength');
    if (minLengthError) return `Debe tener al menos ${minLengthError.requiredLength} caracteres.`;

    return 'Campo inválido.';
  }
}
