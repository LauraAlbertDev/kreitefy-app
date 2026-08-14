export interface Cancion {
  id: number;
  titulo: string;
  artista: string;
  estilo: string;
  album: string;
  imagenAlbum: string;
  duracionSegundos: number;
  valoracion: number;
  reproducciones: number;
  fecha: Date;
}
