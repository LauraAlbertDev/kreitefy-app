import { Component, DestroyRef, inject, Input, OnInit, signal } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ArtistasService } from '../../../../services/artistas-service';
import { FormField, PreviewConfig } from '../../../../shared/form-field';
import { CancionesService } from '../../../../services/canciones-service';
import { Cancion } from '../../../../shared/cancion';
import { FormValidators } from '../../../../validators/FormValidators';
import { EstilosService } from '../../../../services/estilos-service';
import { AlbumesService } from '../../../../services/albumes-service';
import { forkJoin } from 'rxjs';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { Album } from '../../../../shared/album';
import { ResolverMap } from '../../../../shared/generic';
import { GenericEdit } from '../../generic/generic-edit/generic-edit';
import { ToastService } from '../../../../services/toast-service';

const SELECT_PAGE_SIZE = 0;

@Component({
  selector: 'app-canciones-edit',
  templateUrl: './canciones-edit.html',
  styleUrl: './canciones-edit.scss',
  imports: [
    GenericEdit
  ]
})
export class CancionesEdit implements OnInit{
  private fb = inject(FormBuilder);
  private readonly destroyRef = inject(DestroyRef);
  private readonly toastService = inject(ToastService);
  protected cancionesService = inject(CancionesService);
  protected artistasService = inject(ArtistasService);
  protected estilosService = inject(EstilosService);
  protected albumesService = inject(AlbumesService);
  @Input('id') id?: number;

  protected formFields = signal<FormField<Cancion>[]>([]);
  private albumsData: Album[] = [];
  protected readonly previewConfig: PreviewConfig = {
    titleFields: ['titulo', 'artista'],
    subtitleFields : ['album'],
    imageField: 'imagenAlbum',
    highlightFields: [
      'estilo']
  };

  protected readonly form = this.fb.group({
    id: [],
    titulo: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(50)]],
    artista: ['', [Validators.required]],
    estilo: ['', [Validators.required]],
    album: ['', [Validators.required]],
    imagenAlbum: ['', [Validators.required,  FormValidators.allowedExtension(['jpeg', 'jpg', 'png'])]],
    fecha: ['', [Validators.required]],
    duracionSegundos: ['', [Validators.required]],
  });

  protected readonly resolvers: ResolverMap<Cancion> = {
    artista: (val: string)=> val?.trim(),
    estilo: (val: string)=> val?.trim(),
    album: (val: string)=> val?.trim(),
  }

  ngOnInit() {
    this.loadSelectOptions();
    this.form.get('album')?.valueChanges
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe(tituloSeleccionado => {
        if (!tituloSeleccionado) return;

        const albumEncontrado = this.albumsData.find(a => a.titulo === tituloSeleccionado);
        if (albumEncontrado?.imagen) {
          this.form.patchValue({ imagenAlbum: albumEncontrado.imagen });
        }
      });
  }

  private loadSelectOptions() {
    forkJoin({
      artistas: this.artistasService.findAllUnpaged(),
      albums: this.albumesService.findAllUnpaged(),
      estilos: this.estilosService.findAllUnpaged(),
    })
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: ({ artistas, estilos, albums }) => {
          this.albumsData = (albums.content ?? []) as Album[];
          const artistasOpts = (artistas.content ?? []).map(a => a.nombre);
          const estilosOpts = (estilos.content ?? []).map(e => e.nombre);
          const albumsOpts = this.albumsData.map(al => al.titulo);
          this.formFields.set(this.buildFields(artistasOpts, estilosOpts, albumsOpts));        },
        error: (error) => {
          this.toastService.show({
            title: 'Error al enviar el formulario',
            body: 'No se pudieron enviar los datos.',
            classname: 'bg-danger text-light',
            delay: 5000
          })
        },
      })
  }

  private buildFields(artistas: string[], estilos: string[], albums: string[]): FormField<Cancion>[] {
    return [
      { name: 'titulo', label: 'Título', type: 'text' },
      { name: 'artista', label: 'Artista', type: 'select', options: artistas },
      { name: 'estilo', label: 'Estilo', type: 'select', options: estilos },
      { name: 'album', label: 'Álbum', type: 'select', options: albums },
      { name: 'duracionSegundos', label: 'Duración (seg)', type: 'text' },
      { name: 'fecha', label: 'Fecha', type: 'date' },
    ];
  }
}
