import { Component, inject } from '@angular/core';
import { GenericEdit } from '../../generic/generic-edit/generic-edit';
import { FormBuilder, Validators } from '@angular/forms';
import { EstilosService } from '../../../../services/estilos-service';
import { FormField, PreviewConfig } from '../../../../shared/form-field';
import { Base } from '../../../../shared/base';
import { ArtistasService } from '../../../../services/artistas-service';

@Component({
  selector: 'app-artistas-edit',
  imports: [
    GenericEdit
  ],
  templateUrl: './artistas-edit.html',
  styleUrl: './artistas-edit.scss',
})
export class ArtistasEdit {
  private fb = inject(FormBuilder);
  protected artistasService = inject(ArtistasService);
  protected readonly previewConfig: PreviewConfig = {
    titleFields: ['nombre'],
  };

  protected readonly formFields: FormField<Base>[] = [
    {
      name: 'nombre',
      label: 'Nombre ',
      type: 'text',
    }
  ];

  protected readonly form = this.fb.group({
    id: [],
    nombre: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(50)]],
  });
}
