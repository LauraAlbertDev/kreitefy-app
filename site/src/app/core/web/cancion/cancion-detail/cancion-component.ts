import {
  Component,
  DestroyRef,
  inject,
  Input,
  OnInit,
  signal,
  WritableSignal,
} from '@angular/core';
import { Router } from '@angular/router';
import { Cancion, Valoracion } from '../../../../shared/interfaces';
import { ValoracionService } from '../../../../services/crud/valoracion-service';
import { AuthService } from '../../../../services/auth/auth-service';
import { CancionService } from '../../../../services/crud/cancion-service';
import { forkJoin } from 'rxjs';
import { ReproduccionService } from '../../../../services/crud/reproduccion-service';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { ToastService } from '../../../../services/toast-service';

@Component({
  selector: 'app-cancion',
  imports: [],
  templateUrl: './cancion-component.html',
  styleUrl: './cancion-component.scss',
})
export class CancionComponent implements OnInit {
  @Input() id!: string;

  private readonly cancionService = inject(CancionService);
  private readonly valoracionService = inject(ValoracionService);
  private readonly reproduccionService = inject(ReproduccionService);
  private readonly authService = inject(AuthService);
  private readonly toastService = inject(ToastService);

  private readonly router = inject(Router);
  private readonly destroyRef = inject(DestroyRef);

  cancion: WritableSignal<Cancion | null> = signal(null);
  valoracion: WritableSignal<Valoracion | null> = signal(null);
  estrellas = [1, 2, 3, 4, 5];
  isPlaying = false;

  ngOnInit(): void {
    this.loadCancion(this.id);
    this.loadValoracion(this.id);
  }

  private loadCancion(id: string): void {
    this.cancionService.getCancion(id).subscribe({
      next: (data) => this.cancion.set(data),
      error: (err) => console.error(err),
    });
  }

  private loadValoracion(cancionId: string) {
    this.valoracionService.getValoracionByCancionIdAndUsuarioId(cancionId).subscribe({
      next: (response) => {
        this.valoracion.set(response);
      },
      error: (err) => {
        if (err.status === 404) {
          this.valoracion.set(null);
        } else {
          this.toastService.show({
            title: 'Error',
            body: 'Error al obtener los datos',
            classname: 'bg-danger text-light',
            delay: 4000,
          });
        }
      },
    });
  }

  valorar(valoracion: number): void {
    const usuarioId = this.authService.currentUserId();
    if (!usuarioId) return;

    const actual = this.valoracion();

    // DELETE
    if (actual && actual.valoracion === valoracion) {
      this.valoracionService.eliminarValoracion(actual.id.toString()).subscribe({
        next: () => {
          this.valoracion.set(null);
          this.loadCancion(this.id);
        },
        error: (err) => console.error(err),
      });
      return;
    }

    // UPDATE
    if (actual) {
      const updated = {
        id: actual.id,
        usuarioId: Number(usuarioId),
        cancionId: Number(this.id),
        valoracion,
      };

      this.valoracionService.actualizarValoracion(actual.id.toString(), updated).subscribe({
        next: (res) => {
          this.valoracion.set(res);
          this.loadCancion(this.id);
        },
        error: (err) => console.error(err),
      });

      return;
    }

    // CREATE
    const nueva = {
      usuarioId: Number(usuarioId),
      cancionId: Number(this.id),
      valoracion,
    };

    this.valoracionService.registrarValoracion(nueva as any).subscribe({
      next: (res) => {
        this.valoracion.set(res);
        this.loadCancion(this.id);
      },
      error: (err) => console.error(err),
    });
  }

  botonPlay(): void {
    if (!this.cancion) return;
    this.isPlaying = !this.isPlaying;
    if (this.isPlaying) {
      this.registrarReproduccion();
    }
  }

  private registrarReproduccion() {

    this.reproduccionService.registerReproduccion({
      cancionId: Number(this.id),
    })
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: () => this.loadCancion(this.id),
        error: () => this.toastService.show({
          title: 'Error',
          body: 'Error al registrar reproducción',
          classname: 'bg-danger text-light',
          delay: 4000,
        }),
      });
  }

  volver(): void {
    void this.router.navigate(['/inicio']);
  }
}
