import { Component, inject } from '@angular/core';
import { NgbToast } from '@ng-bootstrap/ng-bootstrap';
import { ToastService } from '../../../services/toast-service';

@Component({
  selector: 'app-toast',
  imports: [
    NgbToast
  ],
  templateUrl: './toast.html',
  styleUrl: './toast.scss',
})
export class Toast {
  protected readonly toastService = inject(ToastService);

}
