export interface Usuario {
  id: string;
  username: string;
  nombre: string;
  apellidos: string;
  email: string;
  password: string;
  rol: Rol;
}

export enum Rol {
  ADMIN = 'ADMIN',
  USUARIO = 'USUARIO',
}

