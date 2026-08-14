import { Component, inject } from '@angular/core';
import { GenericEdit } from '../../generic/generic-edit/generic-edit';
import { FormBuilder, Validators } from '@angular/forms';
import { ArtistasService } from '../../../../services/artistas-service';
import { FormField, PreviewConfig } from '../../../../shared/form-field';
import { Base } from '../../../../shared/base';
import { Album } from '../../../../shared/album';
import { AlbumesService } from '../../../../services/albumes-service';
import { FormValidators } from '../../../../validators/FormValidators';

@Component({
  selector: 'app-albumes-edit',
  imports: [
    GenericEdit
  ],
  templateUrl: './albumes-edit.html',
  styleUrl: './albumes-edit.scss',
})
export class AlbumesEdit {
  private fb = inject(FormBuilder);
  protected albumesService = inject(AlbumesService);
  protected readonly previewConfig: PreviewConfig = {
    titleFields: ['titulo'],
    imageField: 'imagen',
  };

  protected readonly formFields: FormField<Album>[] = [
    {
      name: 'titulo',
      label: 'Titulo ',
      type: 'text',
    },
    {
      name: 'imagen',
      label: 'Imagen ',
      type: 'file',
    }
  ];

  protected readonly form = this.fb.group({
    id: [],
    titulo: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(50)]],
    imagen: ['', [Validators.required, FormValidators.allowedExtension(['jpeg', 'jpg', 'png'])]],
  });
}
