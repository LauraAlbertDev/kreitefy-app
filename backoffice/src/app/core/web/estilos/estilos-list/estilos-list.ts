import { Component, inject, signal } from '@angular/core';
import { GenericList } from '../../generic/generic-list/generic-list';
import { UsuariosService } from '../../../../services/usuarios-service';
import { TableColumn } from '../../../../shared/table-column';
import { EstilosService } from '../../../../services/estilos-service';
import { Base } from '../../../../shared/base';

@Component({
  selector: 'app-estilos-list',
  imports: [
    GenericList
  ],
  templateUrl: './estilos-list.html',
  styleUrl: './estilos-list.scss',
})
export class EstilosList {
  protected readonly estilosService = inject(EstilosService);
  protected columns = signal<TableColumn<Base>[]>([
    { field: 'id', header: 'ID' },
    { field: 'nombre', header: 'Nombre' }
  ]);
}
