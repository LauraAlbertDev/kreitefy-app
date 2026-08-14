import { Component, inject, input, OnInit, output, signal } from '@angular/core';
import { BaseService } from '../../../../services/base-service';
import { NgbPagination } from '@ng-bootstrap/ng-bootstrap';
import { RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { DEFAULT_PAGE_SIZE } from '../../../../shared/page';
import { TruncatePipe } from '../../../../shared/pipes/truncate-pipe';
import { HttpErrorResponse } from '@angular/common/http';
import { ToastService } from '../../../../services/toast-service';
import { ModalComponent } from '../../../../components/modal/modal.component';
import { ListColumn } from '../../../../shared/generic';

@Component({
  selector: 'app-generic-list',
  imports: [NgbPagination, RouterLink, FormsModule, TruncatePipe, ModalComponent],
  templateUrl: './generic-list.html',
  styleUrl: './generic-list.scss',
})
export class GenericList<T extends Record<string, any>> implements OnInit {
  protected readonly toastService = inject(ToastService);

  buttonLabel = input.required<string>();
  service = input.required<BaseService<any>>();
  resourceName = input.required<string>();
  columns = input.required<ListColumn[]>();
  idField = input<keyof T | 'id'>('id');
  pageSize = DEFAULT_PAGE_SIZE;
  deleted = output<number>();
  pageChange = output<number>();

  public currentPage = signal<number>(1);
  public data = signal<T[]>([]);
  public totalElements = signal<number>(0);
  public loaded = signal<boolean>(false);
  protected idParaEliminar: number | null = null;
  protected mostrarModal = signal(false);

  ngOnInit() {
    this.loadData(0);
  }

  public loadData(page: number) {
    this.service()
      .findAll(page, this.pageSize)
      .subscribe({
        next: (res) => {
          this.data.set(res.content || []);
          this.totalElements.set(res.totalElements || 0);
          this.currentPage.set((res.number ?? page) + 1);
          this.loaded.set(true);
        },
        error: (err) => {
          this.toastService.show(
            {
              title: 'Error al cargar los datos',
              body: 'No se pudieron cargar los datos.',
              classname: 'bg-danger text-light',
              delay: 5000
            }
          )
        }
      });
  }

  public onDelete(id: unknown) {
    const numericId = Number(id);
    if (!isNaN(numericId)) {
      this.idParaEliminar = numericId;
      this.mostrarModal.set(true);
    }
  }

  protected confirmarBorrado() {
    if (this.idParaEliminar === null) return;

    this.service()
      .delete(this.idParaEliminar)
      .subscribe({
        next: () => {
          this.mostrarModal.set(false);
          this.toastService.show({
            title: 'Eliminado',
            body: 'El registro se ha borrado correctamente.',
            classname: 'bg-success text-light',
            delay: 4000
          });
          this.loadData(0);
          this.deleted.emit(this.idParaEliminar!);
          this.idParaEliminar = null;
        },
        error: (error: HttpErrorResponse) => {
          this.mostrarModal.set(false);
          const serverMsg = error.error?.message || 'No se puede eliminar el registro porque tiene dependencias activas.';

          this.toastService.show({
            title: 'Error al eliminar',
            body: serverMsg,
            classname: 'bg-danger text-light',
            delay: 6000
          });
          this.idParaEliminar = null;
        }
      });
  }

  protected getValue(row: T, field: string): string {
    const value = row[field];
    return value !== null && value !== undefined ? String(value) : '';
  }

  protected onPageChange(newPage: number) {
    this.loadData(newPage - 1);
    this.pageChange.emit(newPage);
  }
}
