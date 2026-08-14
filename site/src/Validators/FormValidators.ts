import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';

export const matchPasswordsValidator: ValidatorFn = (
  control: AbstractControl,
): ValidationErrors | null => {
  const password = control.get('password');
  const confirmPassword = control.get('confirmPassword');

  const passVal = password?.value || '';
  const confirmVal = confirmPassword?.value || '';

  if (!passVal && !confirmVal) {
    if (confirmPassword?.hasError('passwordsMismatch')) {
      limpiarError(confirmPassword);
    }
    return null;
  }

  if (passVal !== confirmVal) {
    confirmPassword?.setErrors({ passwordsMismatch: true });
    return { passwordsMismatch: true };
  }

  if (confirmPassword?.hasError('passwordsMismatch')) {
    limpiarError(confirmPassword);
  }

  return null;
};

function limpiarError(control: AbstractControl) {
  const errors = control.errors;
  if (errors) {
    delete errors['passwordsMismatch'];
    control.setErrors(Object.keys(errors).length ? errors : null);
  }
}
