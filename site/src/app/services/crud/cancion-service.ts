import { inject, Injectable } from '@angular/core';
import { environment } from '../../../environments/environment.development';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Cancion, CancionSimple } from '../../shared/interfaces';

@Injectable({
  providedIn: 'root',
})
export class CancionService {
  private readonly baseUrl = environment.baseURL + '/canciones';
  private readonly http = inject(HttpClient);

  getNovedades(estiloId?: number): Observable<CancionSimple[]> {
    let url = this.baseUrl + '/novedades';
    if (estiloId) {
      url += `?estilo=${estiloId}`;
    }
    return this.http.get<CancionSimple[]>(url);
  }

  getHits(estiloId?: number): Observable<CancionSimple[]> {
    let url = this.baseUrl + '/hits';
    if (estiloId) {
      url += `?estilo=${estiloId}`;
    }
    return this.http.get<CancionSimple[]>(url);
  }

  getFYP(): Observable<CancionSimple[]> {
    return this.http.get<CancionSimple[]>(this.baseUrl + '/fyp' );
  }

  getEstilosUsuario(): Observable<string[]> {
    return this.http.get<string[]>(this.baseUrl + '/estilos' );
  }

  getCancion(id: string): Observable<Cancion> {
    return this.http.get<Cancion>(this.baseUrl + '/' + id);
  }

  getCanciones(params: any): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/filter`, { params });
  }

  getSugerencias(filtros: any): Observable<string[]> {
    let params = new HttpParams();

    Object.keys(filtros).forEach((key) => {
      if (filtros[key] !== null && filtros[key] !== undefined && filtros[key] !== '') {
        params = params.set(key, filtros[key]);
      }
    });

    return this.http.get<string[]>(`${this.baseUrl}/sugerencias`, { params });
  }
}
