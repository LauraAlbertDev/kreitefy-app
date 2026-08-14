import { Component, inject } from '@angular/core';
import { NgbToast } from '@ng-bootstrap/ng-bootstrap';
import { ToastService } from '../../services/toast-service';

@Component({
  selector: 'app-toast-component',
  imports: [NgbToast],
  templateUrl: './toast-component.html',
  styleUrl: './toast-component.scss',
})
export class ToastComponent {
  protected readonly toastService = inject(ToastService);
}
