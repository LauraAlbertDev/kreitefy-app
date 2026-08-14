import { Component, inject, signal } from '@angular/core';
import { GenericList } from '../../generic/generic-list/generic-list';
import { TableColumn } from '../../../../shared/table-column';
import { ArtistasService } from '../../../../services/artistas-service';
import { Base } from '../../../../shared/base';

@Component({
  selector: 'app-artistas-list',
  imports: [
    GenericList
  ],
  templateUrl: './artistas-list.html',
  styleUrl: './artistas-list.scss',
})
export class ArtistasList {
  protected readonly artistasService = inject(ArtistasService);
  protected columns = signal<TableColumn<Base>[]>([
    { field: 'id', header: 'ID' },
    { field: 'nombre', header: 'Nombre' }
  ]);
}
