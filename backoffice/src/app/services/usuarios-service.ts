import { Injectable } from '@angular/core';
import { BaseService } from './base-service';
import { Usuario } from '../shared/usuario';

@Injectable({
  providedIn: 'root',
})
export class UsuariosService extends BaseService<Usuario> {
  protected override itemEndpoint: string = 'usuario';
  protected readonly listEndpoint = 'usuarios';
}

