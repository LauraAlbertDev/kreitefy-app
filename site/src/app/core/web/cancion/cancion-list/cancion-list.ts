import { Component, inject, OnInit, signal } from '@angular/core';
import { CancionService } from '../../../../services/crud/cancion-service';
import { Cancion } from '../../../../shared/interfaces';
import { NgbPagination, NgbTypeahead } from '@ng-bootstrap/ng-bootstrap';
import { SongCard } from '../../../components/song-card/song-card';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import {
  debounceTime,
  delay,
  distinctUntilChanged,
  Observable,
  of,
  OperatorFunction,
  switchMap,
} from 'rxjs';
import { EstiloService } from '../../../../services/crud/estilo-service';
import { FilterConfig } from '../../../../shared/filter-config';

@Component({
  selector: 'app-cancion-list',
  imports: [NgbPagination, SongCard, ReactiveFormsModule, NgbTypeahead],
  templateUrl: './cancion-list.html',
  styleUrl: './cancion-list.scss',
})
// ... (imports iguales)
export class CancionList implements OnInit {
  private readonly service: CancionService = inject(CancionService);
  private readonly estilosService = inject(EstiloService);
  private readonly fb = inject(FormBuilder);

  public data = signal<Cancion[]>([]);
  public totalElements = signal<number>(0);
  public currentPage = signal<number>(1);
  public loaded = signal<boolean>(false);
  public estilos = signal<any[]>([]);
  public pageSize = 20;
  formatter = (result: string) => result;
  searchTitulo = (text$: Observable<string>) => this.searchSugerencias('titulo')(text$);
  searchArtista = (text$: Observable<string>) => this.searchSugerencias('artista.nombre')(text$);
  searchAlbum = (text$: Observable<string>) => this.searchSugerencias('album.titulo')(text$);

  public filtrosForm = this.fb.group({
    idEstilo: [''],
    titulo: [''],
    artista: [''],
    album: [''],
  });

  public readonly filtersConfig: FilterConfig[] = [
    {
      key: 'titulo',
      label: 'Título',
      placeholder: 'Buscar canción...',
      col: 'col-md-2',
      searchFn: this.searchTitulo,
    },
    {
      key: 'artista',
      label: 'Artista',
      placeholder: 'Escribe un artista...',
      col: 'col-md-2',
      searchFn: this.searchArtista,
    },
    {
      key: 'album',
      label: 'Álbum',
      placeholder: 'Escribe un álbum...',
      col: 'col-md-2',
      searchFn: this.searchAlbum,
    },
  ];

  public searchSugerencias(campo: string): OperatorFunction<string, readonly string[]> {
    return (text$: Observable<string>) =>
      text$.pipe(
        debounceTime(300),
        distinctUntilChanged(),
        switchMap((term) => {
          if (term.length < 1) return of([]);

          // 1. Obtenemos lo que hay escrito en TODO el formulario actualmente
          const filtrosActuales = this.filtrosForm.getRawValue();

          // 2. Creamos el objeto de envío
          // 'term' es lo que el usuario escribe ahora mismo en este input concreto
          // 'campo' le dice al backend qué columna debe devolver (artista, titulo, etc.)
          const payload = {
            ...filtrosActuales,
            term: term,
            campo: campo,
          };

          return this.service.getSugerencias(payload);
        }),
      );
  }
  ngOnInit() {
    this.cargarEstilos();
    this.load(0);
    this.filtrosForm.valueChanges
      .pipe(debounceTime(200), distinctUntilChanged())
      .subscribe(() => this.load(0));
  }

  private load(page: number) {
    this.loaded.set(false);
    const formValues = this.filtrosForm.value;

    const params: any = {
      page: page,
      size: this.pageSize,
      ...formValues,
    };

    Object.keys(params).forEach((key) => {
      if (params[key] === null || params[key] === '' || params[key] === undefined) {
        delete params[key];
      }
    });

    this.service.getCanciones(params).subscribe({
      next: (res: any) => {
        this.data.set(res.content || res);
        this.totalElements.set(res.totalElements || 0);
        this.currentPage.set((res.number ?? 0) + 1);
        this.loaded.set(true);
      },
      error: (err) => {
        this.loaded.set(true);
        console.error('Error cargando canciones:', err);
      },
    });
  }

  public limpiarFiltros() {
    this.filtrosForm.reset({
      idEstilo: '',
      titulo: '',
      artista: '',
      album: '',
    });
  }

  protected onPageChange(newPage: number) {
    this.load(newPage - 1);
  }

  private cargarEstilos() {
    this.estilosService.getAllEstilos().subscribe({
      next: (res) => this.estilos.set(res || []),
      error: (err) => console.error('Error cargando estilos:', err),
    });
  }
}
