import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from '../../shared/interfaces';
import { environment } from '../../../environments/environment';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class UsuariosService {
  private readonly usuariosUrl: string = environment.baseURL + '/usuarios';
  private readonly http: HttpClient = inject(HttpClient);

  getUsuario(): Observable<User> {
    return this.http.get<User>(`${this.usuariosUrl}/profile/me`);
  }

  update(usuario: User): Observable<User[]> {
    return this.http.put<User[]>(`${this.usuariosUrl}/profile/me`, usuario);
  }
}
