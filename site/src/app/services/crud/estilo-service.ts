import { inject, Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Estilo } from '../../shared/interfaces';

@Injectable({
  providedIn: 'root',
})
export class EstiloService {
  private readonly baseUrl = environment.baseURL + '/estilos';
  private readonly http = inject(HttpClient);

  getAllEstilos(): Observable<Estilo[]> {
    return this.http.get<Estilo[]>(this.baseUrl + '/all');
  }
}
