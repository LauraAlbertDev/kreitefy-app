import { computed, inject, Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable, tap } from 'rxjs';
import { environment } from '../../../environment/environment';
import { LoginCredentials } from '../../shared/login';
import { Usuario } from '../../shared/usuario';
import { ApiError } from '../../shared/error-messages';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private readonly baseUrl = environment.baseUrl + 'auth';

  private readonly http = inject(HttpClient);
  private readonly router = inject(Router);

  readonly isLoggedIn = signal(false);

  readonly currentUser = signal<Usuario | null>(null);

  readonly currentUserId = computed(() => this.currentUser()?.id ?? null);

  readonly currentUserRole = computed(() => this.currentUser()?.rol ?? null);

  readonly currentUserFullName = computed(() => {
    const user = this.currentUser();

    if (!user) return '';

    return `${user.nombre} ${user.apellidos}`;
  });
  login(credentials: LoginCredentials): Observable<Usuario> {
    return this.http
      .post<Usuario>(`${this.baseUrl}/login`, credentials, {
        withCredentials: true,
      })
      .pipe(
        tap((user) => {
          this.currentUser.set(user);

          this.isLoggedIn.set(true);
        }),
      );
  }

  register(user: Usuario): Observable<Usuario | ApiError> {
    return this.http.post<Usuario | ApiError>(`${this.baseUrl}/register`, user, {
      withCredentials: true,
    });
  }

  restoreSession(): Observable<Usuario> {
    return this.http
      .get<Usuario>(`${this.baseUrl}/refresh/me`, {
        withCredentials: true,
      })
      .pipe(
        tap((user) => {
          this.currentUser.set(user);

          this.isLoggedIn.set(true);
        }),
      );
  }

  logout(): void {
    this.http
      .post(
        `${this.baseUrl}/logout`,
        {},
        {
          withCredentials: true,
        },
      )
      .subscribe(() => {
        this.currentUser.set(null);

        this.isLoggedIn.set(false);

        this.router.navigate(['/login']);
      });
  }
}
