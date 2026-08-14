import { Routes } from '@angular/router';
import { UsuariosList } from './core/web/usuarios/usuarios-list/usuarios-list';
import { Login } from './core/web/login/login';
import { UsuariosEdit } from './core/web/usuarios/usuarios-edit/usuarios-edit';
import { roleGuard } from './services/auth/auth-guard';
import { Home } from './core/web/home/home';
import { EstilosList } from './core/web/estilos/estilos-list/estilos-list';
import { EstilosEdit } from './core/web/estilos/estilos-edit/estilos-edit';
import { ArtistasList } from './core/web/artistas/artistas-list/artistas-list';
import { ArtistasEdit } from './core/web/artistas/artistas-edit/artistas-edit';
import { AlbumesList } from './core/web/albumes/albumes-list/albumes-list';
import { AlbumesEdit } from './core/web/albumes/albumes-edit/albumes-edit';
import { CancionesList } from './core/web/canciones/canciones-list/canciones-list';
import { CancionesEdit } from './core/web/canciones/canciones-edit/canciones-edit';

export const routes: Routes = [
  {
    path: '',
    component: Home,
    pathMatch: 'full',
  },
  {
    path: 'usuarios',
    canActivate: [roleGuard(['ADMIN'])],
    children: [
      { path: '', component: UsuariosList },
      { path: 'create', component: UsuariosEdit },
      { path: 'edit/:id', component: UsuariosEdit },
    ]
  },
  {
    path: 'estilos',
    canActivate: [roleGuard(['ADMIN'])],
    children: [
      { path: '', component: EstilosList },
      { path: 'create', component: EstilosEdit },
      { path: 'edit/:id', component: EstilosEdit },
    ]
  },
  {
    path: 'artistas',
    canActivate: [roleGuard(['ADMIN'])],
    children: [
      { path: '', component: ArtistasList },
      { path: 'create', component: ArtistasEdit },
      { path: 'edit/:id', component: ArtistasEdit },
    ]
  },
  {
    path: 'albums',
    canActivate: [roleGuard(['ADMIN'])],
    children: [
      { path: '', component: AlbumesList },
      { path: 'create', component: AlbumesEdit },
      { path: 'edit/:id', component: AlbumesEdit },
    ]
  },
  {
    path: 'canciones',
    canActivate: [roleGuard(['ADMIN'])],
    children: [
      { path: '', component: CancionesList },
      { path: 'create', component: CancionesEdit },
      { path: 'edit/:id', component: CancionesEdit },
    ]
  },
  {
    path: 'login',
    component: Login,
  }
];
