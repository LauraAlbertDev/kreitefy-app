import { Component, inject } from '@angular/core';
import { GenericEdit } from '../../generic/generic-edit/generic-edit';
import { FormBuilder, Validators } from '@angular/forms';
import { UsuariosService } from '../../../../services/usuarios-service';
import { Rol, Usuario } from '../../../../shared/usuario';
import { FormField, PreviewConfig } from '../../../../shared/form-field';
import { EstilosService } from '../../../../services/estilos-service';
import { Base} from '../../../../shared/base';

@Component({
  selector: 'app-estilos-edit',
  imports: [
    GenericEdit
  ],
  templateUrl: './estilos-edit.html',
  styleUrl: './estilos-edit.scss',
})
export class EstilosEdit {

  private fb = inject(FormBuilder);
  protected estilosService = inject(EstilosService);
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
