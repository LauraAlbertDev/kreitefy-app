import { Routes } from '@angular/router';
import {Inicio} from './core/web/inicio/inicio';
import { Login } from './core/web/login/login';
import { Register } from './core/web/register/register';
import { Landing } from './core/web/landing/landing';
import { roleGuard } from './services/auth/auth-guard';
import { CancionList } from './core/web/cancion/cancion-list/cancion-list';
import { CancionComponent } from './core/web/cancion/cancion-detail/cancion-component';
import { Perfil } from './core/web/perfil/perfil';

export const routes: Routes = [
  {
    path: '',
    component: Inicio,
    pathMatch: 'full',
  },
  {
    path: 'inicio',
    component: Inicio,
  },
  {
    path: 'login',
    component: Login,
  },
  {
    path: 'register',
    component: Register,
  },
  {
    path: 'canciones',
    component: CancionList,
    canActivate: [roleGuard(['ADMIN', 'USUARIO'])],
  },
  {
    path: 'cancion/:id',
    component: CancionComponent,
    canActivate: [roleGuard(['ADMIN', 'USUARIO'])],
  },
  {
    path: 'landing',
    component: Landing,
    canActivate: [roleGuard(['ADMIN', 'USUARIO'])],
  },
  {
    path: 'perfil',
    component: Perfil,
    canActivate: [roleGuard(['ADMIN', 'USUARIO'])],
  },
];
