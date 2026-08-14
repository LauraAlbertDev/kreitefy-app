import { computed, inject, Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable, tap } from 'rxjs';
import { environment } from '../../../environments/environment';
import { ApiError, LoginCredentials, User } from '../../shared/interfaces';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private readonly baseUrl = environment.baseURL + '/auth';

  private readonly http = inject(HttpClient);
  private readonly router = inject(Router);

  readonly isLoggedIn = signal(false);

  readonly currentUser = signal<User | null>(null);

  readonly currentUserId = computed(() => this.currentUser()?.id ?? null);

  readonly currentUserRole = computed(() => this.currentUser()?.rol ?? null);

  readonly currentUserFullName = computed(() => {
    const user = this.currentUser();

    if (!user) return '';

    return `${user.nombre} ${user.apellidos}`;
  });
  login(credentials: LoginCredentials): Observable<User> {
    return this.http
      .post<User>(`${this.baseUrl}/login`, credentials, {
        withCredentials: true,
      })
      .pipe(
        tap((user) => {
          this.currentUser.set(user);

          this.isLoggedIn.set(true);
        }),
      );
  }

  register(user: User): Observable<User | ApiError> {
    return this.http
      .post<User | ApiError>(`${this.baseUrl}/register`, user, {
        withCredentials: true,
      })
      .pipe(
        tap((response) => {
          if (this.isUser(response)) {
            this.currentUser.set(response);
            this.isLoggedIn.set(true);
          }
        }),
      );
  }

  restoreSession(): Observable<User> {
    return this.http
      .get<User>(`${this.baseUrl}/refresh/me`, {
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

  private isUser(obj: User | ApiError): obj is User {
    return 'email' in obj;
  }
}
