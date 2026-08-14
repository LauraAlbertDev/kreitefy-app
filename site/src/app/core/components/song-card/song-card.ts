import { Component, Input } from '@angular/core';
import { RouterLink } from '@angular/router';
import { CancionSimple } from '../../../shared/interfaces';

@Component({
  selector: 'app-song-card',
  imports: [RouterLink],
  templateUrl: './song-card.html',
  styleUrl: './song-card.scss',
})
export class SongCard {
  @Input() cancion!: CancionSimple;

  protected getImagenAlbum(imagenAlbum: string) {
    return imagenAlbum?.length ? imagenAlbum : 'assets/album_placeholder.png';
  }
}
