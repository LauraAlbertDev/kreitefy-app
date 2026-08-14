import { Component, inject, OnInit, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Navbar } from './struct/navbar/navbar';
import { Footer } from './struct/footer/footer';
import { ToastComponent } from './core/components/toast-component/toast-component';
import { ToastService } from './services/toast-service';
import { AuthService } from './services/auth/auth-service';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, Navbar, Footer, ToastComponent],
  templateUrl: './app.html',
  styleUrl: './app.scss',
})
export class App implements OnInit {
  protected readonly title = signal('site');
  private readonly authService = inject(AuthService);
  private readonly toastService = inject(ToastService);

  ngOnInit() {
    this.authService.restoreSession().subscribe({
      next: () => {
        this.toastService.show({
          title: 'Bienvenido',
          body: 'Se ha recuperado su sesión correctamente',
          classname: 'bg-success text-light',
          delay: 4000,
        });
      },
      error: () => {
        this.toastService.show({
          title: 'Sin sesión',
          body: 'Actualmente no ha iniciado sesión.',
          classname: 'bg-warning text-light',
          delay: 4000,
        });
      }
      });
  }
}
