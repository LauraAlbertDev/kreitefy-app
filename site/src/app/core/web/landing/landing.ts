import { Component, inject, OnInit, signal, WritableSignal, DestroyRef } from '@angular/core';
import { forkJoin } from 'rxjs';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';

import { CancionService } from '../../../services/crud/cancion-service';
import { CancionSimple, Estilo } from '../../../shared/interfaces';
import { ToastService } from '../../../services/toast-service';
import { SongCard } from '../../components/song-card/song-card';
import { EstiloService } from '../../../services/crud/estilo-service';
import { RouterLink } from '@angular/router';
import { AuthService } from '../../../services/auth/auth-service';

@Component({
  selector: 'app-landing',
  imports: [SongCard, RouterLink],
  templateUrl: './landing.html',
  styleUrl: './landing.scss',
})
export class Landing implements OnInit {
  private readonly cancionService = inject(CancionService);
  private readonly estiloService = inject(EstiloService);
  private readonly authService = inject(AuthService);
  private readonly toastService = inject(ToastService);
  private readonly destroyRef = inject(DestroyRef);

  novedades: WritableSignal<CancionSimple[]> = signal([]);
  hits: WritableSignal<CancionSimple[]> = signal([]);
  fyp: WritableSignal<CancionSimple[]> = signal([]);
  estilos: WritableSignal<Estilo[]> = signal([]);
  estilosUsuario: WritableSignal<string[]> = signal([]);

  selectedEstiloId: WritableSignal<number | null> = signal(null);

  ngOnInit() {
    this.loadData();
  }

  private loadData() {
    const estiloId = this.selectedEstiloId() ?? undefined;

    forkJoin({
      novedades: this.cancionService.getNovedades(estiloId),
      hits: this.cancionService.getHits(estiloId),
      fyp: this.cancionService.getFYP(),
      estilos: this.estiloService.getAllEstilos(),
      estilosUsuario: this.cancionService.getEstilosUsuario()
    })
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: ({ novedades, hits, fyp, estilos, estilosUsuario}) => {
          this.novedades.set(novedades);
          this.hits.set(hits);
          this.fyp.set(fyp);
          this.estilos.set(estilos);
          this.estilosUsuario.set(estilosUsuario);
        },
        error: () => {
          this.toastService.show({
            title: 'Error',
            body: 'Error al obtener los datos',
            classname: 'bg-danger text-light',
            delay: 4000,
          });
        },
      });
  }

  onEstiloChange(event: Event) {
    const value = (event.target as HTMLSelectElement).value;
    this.selectedEstiloId.set(value ? Number(value) : null);
    this.loadData();
  }
}
