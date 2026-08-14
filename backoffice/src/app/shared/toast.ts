export interface Toast {
  id: number;
  body: string;
  title?: string;
  classname?: string;
  delay?: number;
  autohide?: boolean;
}
