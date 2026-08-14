export interface User {
  id: number;
  username: string;
  password: string;
  nombre: string;
  apellidos: string;
  email: string;
  rol: string;
}

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
}

export interface CancionSimple {
  id: number;
  titulo: string;
  artista: string;
  imagenAlbum: string;
}

export interface Estilo {
  id: number;
  nombre: string;
}

export interface Valoracion {
  id: number;
  cancionId: number;
  usuarioId: number;
  valoracion: number;
}

export interface RegisterReproduccion {
  cancionId: number;
}

export interface Reproduccion {
  cancionId: number;
  cancionTitulo: string;
  fecha: string;
  id: number;
  usuarioId: number;
}

export interface LoginCredentials {
  username: string;
  password: string;
}

export interface LoginResponse {
  token: string;
}

export interface ApiError {
  field: string;
  message: string;
}

export interface Toast {
  id: number;
  body: string;
  title?: string;
  classname?: string;
  delay?: number;
  autohide?: boolean;
}
