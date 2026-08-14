import { Component, input, output } from '@angular/core';

@Component({
  selector: 'app-modal',
  imports: [],
  templateUrl: './modal.html',
  styleUrl: './modal.scss',
})
export class Modal {
  isOpen = input.required<boolean>();
  title = input<string>('Confirmar acción');
  message = input<string>('¿Estás seguro de que deseas realizar esta acción?');
  onConfirm = output<void>();
  onClose = output<void>();

  protected confirm() {
    this.onConfirm.emit();
  }

  protected close() {
    this.onClose.emit();
  }
}
