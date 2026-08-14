export interface TableColumn<T> {
  header: string;
  field: keyof T;
  pipe?: string;
  pipeFormat?: string;
}
