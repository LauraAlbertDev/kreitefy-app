import { Component, inject } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../../services/auth/auth-service';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ToastService } from '../../../services/toast-service';

@Component({
  selector: 'app-login',
  imports: [RouterLink, ReactiveFormsModule],
  templateUrl: './login.html',
  styleUrl: './login.scss',
})
export class Login {
  private readonly authService: AuthService = inject(AuthService);
  private readonly fb: FormBuilder = inject(FormBuilder);
  private readonly toastService = inject(ToastService);
  private readonly router = inject(Router);

  formLogin: FormGroup = this.fb.group({
    username: ['', [Validators.required]],
    password: ['', [Validators.required]],
  });

  onSubmit(){
    if(this.formLogin.invalid) return;
    this.authService.login(this.formLogin.getRawValue()).subscribe({
      next: (response) => {
        this.toastService.show({
          title: 'Bienvenido',
          body: 'Disfruta de tus canciones favoritas',
          classname: 'bg-success text-light',
          delay: 4000,
        });
        this.router.navigate(['/landing']);
      },
      error: (err) => {
        this.toastService.show({
          title: 'Error',
          body: 'Credenciales incorrectas',
          classname: 'bg-danger text-light',
          delay: 4000,
        })
      }
    })
  }
}
