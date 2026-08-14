import { Component, inject } from '@angular/core';
import { AuthService } from '../../../services/auth/auth-service';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { ToastService } from '../../../services/toast-service';

@Component({
  selector: 'app-register',
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './register.html',
  styleUrl: './register.scss',
})
export class Register {
  private readonly authService: AuthService = inject(AuthService);
  private readonly fb: FormBuilder = inject(FormBuilder);
  private readonly toastService = inject(ToastService);
  private readonly router = inject(Router);

  usuarioForm: FormGroup = this.fb.group({
    id: [null],
    username: ['', [Validators.required, Validators.maxLength(50)]],
    password: ['', [Validators.required]],
    nombre: ['', [Validators.required, Validators.maxLength(50)]],
    apellidos: ['', [Validators.required, Validators.maxLength(100)]],
    email: ['', [Validators.required, Validators.email, Validators.maxLength(100)]],
  });

  get username() {
    return this.usuarioForm.get('username')!;
  }
  get password() {
    return this.usuarioForm.get('password')!;
  }
  get nombre() {
    return this.usuarioForm.get('nombre')!;
  }
  get apellidos() {
    return this.usuarioForm.get('apellidos')!;
  }
  get email() {
    return this.usuarioForm.get('email')!;
  }

  protected onSubmit() {
    this.authService.register(this.usuarioForm.getRawValue()).subscribe({
      next: (response) => {
        this.toastService.show({
          title: 'Registro exitoso',
          body: 'Disfruta escuchando tus canciones favoritas',
          classname: 'bg-success text-light',
          delay: 4000,
        });
        this.router.navigate(['/landing']);
      },
      error: (err: any) => {
        const apiError = err.error;
        if (!apiError){
          this.toastService.show({
            title: 'Error',
            body: 'Unexpected error',
            classname: 'bg-danger text-light',
            delay: 4000,
          });
          return;
        }
        if (apiError.field === 'username') {
          this.toastService.show({
            title: 'Error',
            body: 'El usuario ya existe',
            classname: 'bg-danger text-light',
            delay: 4000,
          });
          return;
        }
        if (apiError.field === 'email') {
          this.toastService.show({
            title: 'Error',
            body: 'El email ya está dado de alta',
            classname: 'bg-danger text-light',
            delay: 4000,
          });
          return;
        }
      },
    });
  }
}
