import { Injectable } from '@angular/core';
import { BaseService } from './base-service';
import { Base } from '../shared/base';

@Injectable({
  providedIn: 'root',
})
export class ArtistasService extends BaseService<Base> {
  protected override itemEndpoint: string = 'artista';
  protected readonly listEndpoint = 'artistas';
}
