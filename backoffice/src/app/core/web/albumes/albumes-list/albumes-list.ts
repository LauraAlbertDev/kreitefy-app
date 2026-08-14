import { Component, inject, signal } from '@angular/core';
import { TableColumn } from '../../../../shared/table-column';
import { AlbumesService } from '../../../../services/albumes-service';
import { Album } from '../../../../shared/album';
import { GenericList } from '../../generic/generic-list/generic-list';

@Component({
  selector: 'app-albumes-list',
  imports: [
    GenericList
  ],
  templateUrl: './albumes-list.html',
  styleUrl: './albumes-list.scss',
})
export class AlbumesList {
  protected readonly albumesService: AlbumesService = inject(AlbumesService);
  protected columns = signal<TableColumn<Album>[]>([
    { field: 'id', header: 'ID' },
    { field: 'titulo', header: 'Titulo' },
    { field: 'imagen', header: 'Imagen' },
  ]);
}
