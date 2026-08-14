import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class FileConverterService {
  convertToBase64(file: File): Observable<string> {
    return new Observable((subscriber) => {
      const reader = new FileReader();

      reader.onload = () => {
        subscriber.next(reader.result as string);
        subscriber.complete();
      };

      reader.onerror = (error) => {
        subscriber.error(error);
      };

      reader.readAsDataURL(file);
    });
  }
}
