import { Pipe, PipeTransform } from '@angular/core';
import { AbstractControl } from '@angular/forms';
import { ERROR_MESSAGES } from '../error-messages';

@Pipe({
  name: 'errorMessage',
  pure: false,
})
export class ErrorMessagePipe implements PipeTransform {
  transform(control: AbstractControl | null, label: string, value?: any): string {
    if (!control || !control.errors) return '';
    const firstErrorKey = Object.keys(control.errors)[0];
    const errorValue = control.errors[firstErrorKey];
    const getMessage = ERROR_MESSAGES[firstErrorKey];
    if (getMessage) {
      return getMessage(label, errorValue);
    }
    return 'Campo no válido';
  }
}
