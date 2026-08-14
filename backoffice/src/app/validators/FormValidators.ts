import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';

export class FormValidators {
  static allowedExtension(allowedTypes: string[]): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      const value = control.value as string;
      if (!value) return null;
      if (value.startsWith('data:image/')) {
        const isValid = allowedTypes.some(type =>
          value.startsWith(`data:image/${type}`)
        );
        return isValid ? null : { allowedExtension: true };
      }
      const extension = value.split('.').pop()?.toLowerCase();
      return allowedTypes.includes(extension!) ? null : { allowedExtension: true };
    };
  }
}
