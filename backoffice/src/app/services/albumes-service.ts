import { Injectable } from '@angular/core';
import { BaseService } from './base-service';
import { Album } from '../shared/album';

@Injectable({
  providedIn: 'root',
})
export class AlbumesService extends BaseService<Album>{
  protected override itemEndpoint: string = 'album';
  protected readonly listEndpoint: string = 'albums';
}
