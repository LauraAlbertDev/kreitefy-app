import { Component, inject, signal } from '@angular/core';
import { TableColumn } from '../../../../shared/table-column';
import { CancionesService } from '../../../../services/canciones-service';
import { Cancion } from '../../../../shared/cancion';
import { GenericList } from '../../generic/generic-list/generic-list';

@Component({
  selector: 'app-canciones-list',
  imports: [
    GenericList
  ],
  templateUrl: './canciones-list.html',
  styleUrl: './canciones-list.scss',
})
export class CancionesList {
  protected readonly cancionesService = inject(CancionesService);
  protected columns = signal<TableColumn<Cancion>[]>([
    { field: 'id', header: 'ID' },
    { field: 'titulo', header: 'Título' },
    { field: 'artista', header: 'Artista' },
    { field: 'estilo', header: 'Estilo' },
    { field: 'album', header: 'Album' },
    { field: 'imagenAlbum', header: 'Imagen Album' },
    { field: 'duracionSegundos', header: 'Duracion Segundos' },
    { field: 'valoracion', header: 'Valoracion' },
    { field: 'reproducciones', header: 'Nº Reproducciones' },
    { field: 'fecha', header: 'Fecha' },
  ]);
}
