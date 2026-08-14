import { Injectable, signal } from '@angular/core';
import { Toast } from '../shared/toast';

@Injectable({
  providedIn: 'root',
})
export class ToastService {
  toasts = signal<Toast[]>([]);

  private id = 0;

  show(toast: Omit<Toast, 'id'>) {
    this.toasts.update((list) => [...list, { ...toast, id: ++this.id }]);
  }

  remove(id: number) {
    this.toasts.update((list) => list.filter((t) => t.id !== id));
  }
}
