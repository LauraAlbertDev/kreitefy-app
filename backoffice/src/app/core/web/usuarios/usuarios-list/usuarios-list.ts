import { Component, inject, signal } from '@angular/core';
import { GenericList } from '../../generic/generic-list/generic-list';
import { Usuario } from '../../../../shared/usuario';
import { UsuariosService } from '../../../../services/usuarios-service';
import { TableColumn } from '../../../../shared/table-column';

@Component({
  selector: 'app-usuarios-list',
  imports: [GenericList],
  templateUrl: './usuarios-list.html',
  styleUrl: './usuarios-list.scss',
})
export class UsuariosList {
  protected readonly usuariosService = inject(UsuariosService);
  protected columns = signal<TableColumn<Usuario>[]>([
    { field: 'id', header: 'ID' },
    { field: 'username', header: 'Nombre de usuario' },
    { field: 'nombre', header: 'Nombre' },
    { field: 'apellidos', header: 'Apellidos' },
    { field: 'email', header: 'Correo Electrónico' },
    { field: 'rol', header: 'Rol' },
  ]);
}
