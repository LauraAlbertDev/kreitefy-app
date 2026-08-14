export interface FormField<T = any> {
  name: string;
  label: string;
  type: 'text' | 'email' | 'number' | 'select' | 'date' | 'file' | 'textarea' | 'password';
  options?: string[];
  pipe?: 'date' | 'currency' | 'percent';
  pipeFormat?: string;
}

export interface PreviewConfig {
  titleFields: string[];
  subtitleFields?: string[];
  imageField?: string;
  detailFields?: string[];
  defaultIcon?: string;
  highlightFields?: string[];
}
