import { Component, inject, OnInit, signal, WritableSignal } from '@angular/core';
import { AuthService } from '../../../services/auth/auth-service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-inicio',
  imports: [],
  templateUrl: './inicio.html',
  styleUrl: './inicio.scss',
})
export class Inicio implements OnInit {
  private readonly authService: AuthService = inject(AuthService);
  private readonly router: Router = inject(Router);
  isLoggedIn = this.authService.isLoggedIn;

  ngOnInit() {
    if (this.isLoggedIn()) {
      this.router.navigate(['/landing']);
    }
  }
}
