import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';
import { AuthService } from './auth-service';

export function roleGuard(allowedRoles: string[]): CanActivateFn {
  return () => {
    const auth = inject(AuthService);
    const router = inject(Router);

    if (!auth.isLoggedIn()) {
      router.navigate(['/login']);
      return false;
    }

    const role = auth.currentUserRole();

    if (role !== null && allowedRoles.includes(role)) {
      return true;
    }
    router.navigate(['/login']);
    return false;
  };
}
