import { inject, Injectable } from '@angular/core';
import { environment } from '../../../environments/environment.development';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Valoracion } from '../../shared/interfaces';

@Injectable({
  providedIn: 'root',
})
export class ValoracionService {
  private readonly baseUrl = environment.baseURL + '/valoraciones';
  private readonly http = inject(HttpClient);

  getValoracionByCancionIdAndUsuarioId(cancionId: string): Observable<Valoracion> {
    return this.http.get<Valoracion>(this.baseUrl + '/cancion/' + cancionId);
  }

  registrarValoracion(valoracion: Valoracion): Observable<Valoracion> {
    return this.http.post<Valoracion>(this.baseUrl, valoracion);
  }

  actualizarValoracion(valoracionId: string,  valoracion: Valoracion): Observable<Valoracion> {
    return this.http.put<Valoracion>(this.baseUrl + '/' + valoracionId, valoracion);
  }

  eliminarValoracion(valoracionId: string): Observable<void> {
    return this.http.delete<void>(this.baseUrl + '/' + valoracionId);
  }

}
