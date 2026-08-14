import { Injectable } from '@angular/core';
import { BaseService } from './base-service';
import { Usuario } from '../shared/usuario';
import { Base } from '../shared/base';

@Injectable({
  providedIn: 'root',
})
export class EstilosService extends BaseService<Base> {
  protected override itemEndpoint: string = 'estilo';
  protected readonly listEndpoint = 'estilos';
}
