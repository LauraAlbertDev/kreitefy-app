import { Component, inject } from '@angular/core';
import { GenericEdit } from '../../generic/generic-edit/generic-edit';
import { FormBuilder, Validators } from '@angular/forms';
import { UsuariosService } from '../../../../services/usuarios-service';
import { Rol, Usuario } from '../../../../shared/usuario';
import { FormField, PreviewConfig } from '../../../../shared/form-field';

@Component({
  selector: 'app-usuarios-edit',
  templateUrl: './usuarios-edit.html',
  styleUrl: './usuarios-edit.scss',
  imports: [
    GenericEdit
  ]
})
export class UsuariosEdit {
  private fb = inject(FormBuilder);
  protected usuariosService = inject(UsuariosService);
  protected readonly availableRoles = Object.values(Rol);
  protected readonly previewConfig: PreviewConfig = {
    titleFields: ['username', 'nombre', 'apellidos'],
    subtitleFields: ['email'],
    highlightFields: ['rol'],
  };

  protected readonly formFields: FormField<Usuario>[] = [
    {
      name: 'username',
      label: 'Nombre de usuario',
      type: 'text',
    },
    {
      name: 'nombre',
      label: 'Nombre',
      type: 'text',
    },
    {
      name: 'apellidos',
      label: 'Apellidos',
      type: 'text',
    },
    {
      name: 'email',
      label: 'Correo',
      type: 'email',
    },
    {
      name: 'rol',
      label: 'Rol',
      type: 'select',
      options: this.availableRoles,
    },
    {
      name: 'password',
      label: 'Contraseña',
      type: 'password',
    },
  ];

  protected readonly form = this.fb.group({
    id: [],
    username: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(100)]],
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
    password: ['', [Validators.required, Validators.minLength(10), Validators.maxLength(100)]],
    rol: ['', [Validators.required]],
  });
}
