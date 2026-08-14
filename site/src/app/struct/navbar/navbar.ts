import { Component, inject } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { NAV_LINKS } from '../../shared/navigation';
import { AuthService } from '../../services/auth/auth-service';

@Component({
  selector: 'app-navbar',
  imports: [RouterLink],
  templateUrl: './navbar.html',
  styleUrl: './navbar.scss',
})
export class Navbar {
  private readonly authService = inject(AuthService);
  private readonly router = inject(Router);

  pages = NAV_LINKS;
  get middleIndex(): number {
    return Math.floor(this.pages.length / 2);
  }

  isLoggedIn = this.authService.isLoggedIn;
  fullName = this.authService.currentUserFullName;

  protected handleAuthAction() {
    if (this.isLoggedIn()) {
      this.authService.logout();
    } else {
      this.router.navigate(['/login']);
    }
  }
}
