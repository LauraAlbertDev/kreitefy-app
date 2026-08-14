export type ResolverMap<T> = {
  [K in keyof T]?: (value: any) => T[K];
}

export interface ApiErrorResponse {
  field: string | null;
  message: string;
}

export interface EntidadAnidada {
  id?: number;
  nombre?: string;
  titulo?: string;
  [key: string]: unknown;
}

export interface ListColumn {
  field: string;
  header: string;
  type?: 'text' | 'date' | 'image' | string;
}
