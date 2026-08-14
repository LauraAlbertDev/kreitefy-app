import { inject, Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { RegisterReproduccion, Reproduccion } from '../../shared/interfaces';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ReproduccionService {
  private readonly baseUrl: string = environment.baseURL + '/reproducciones';
  private readonly http: HttpClient = inject(HttpClient);

  registerReproduccion(registro: RegisterReproduccion): Observable<Reproduccion> {
    return this.http.post<Reproduccion>(this.baseUrl + '/register', registro);
  }

  obtenerReproducciones(): Observable<Reproduccion[]> {
    return this.http.get<Reproduccion[]>(`${this.baseUrl}/tus-reproducciones`);
  }
}
