export const ERROR_MESSAGES: Record<string, (label: string, errorValue?: any) => string> = {
  required: (label) => `El campo ${label} es obligatorio`,
  email: () => 'Formato de email inválido',
  minlength: (label, error) => `El campo ${label} mínimo debe tener ${error.requiredLength} caracteres`,
  min: (label, error) => `El valor mínimo para ${label} es ${error.min}`,
  maxlength: (label, error) => `Máximo ${error.requiredLength} caracteres`,
  pattern: () => 'El formato no es válido',
  exactLength: (label, error) => `El campo ${label} debe tener exactamente ${error.requiredLength} caracteres`,
  allowedExtension: (error) =>
    `La imagen debe tener una extensión .png, .jpg o jpeg para ser validada`,
};

export interface ApiError {
  field: string;
  message: string;
}
