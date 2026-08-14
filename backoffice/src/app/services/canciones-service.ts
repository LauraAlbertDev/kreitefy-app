import { Injectable } from '@angular/core';
import { BaseService } from './base-service';
import { Cancion } from '../shared/cancion';

@Injectable({
  providedIn: 'root',
})
export class CancionesService extends BaseService<Cancion> {
  protected override itemEndpoint: string = 'cancion';
  protected readonly listEndpoint = 'canciones';}
