import { Component, inject, signal } from '@angular/core';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';
import { NAV_LINKS } from '../../shared/navigation';
import { AuthService } from '../../services/auth/auth-service';

@Component({
  selector: 'app-navbar',
  imports: [RouterLink],
  templateUrl: './navbar.html',
  styleUrl: './navbar.scss',
})
export class Navbar {
  pages = NAV_LINKS;
  private readonly authService = inject(AuthService);
  private readonly router = inject(Router);
  get middleIndex(): number {
    return Math.floor(this.pages.length / 2);
  }
  isLoggedIn = this.authService.isLoggedIn;

  protected handleAuthAction() {
    if (this.isLoggedIn()) {
      this.authService.logout();
    } else {
      this.router.navigate(['/login']);
    }
  }
}
