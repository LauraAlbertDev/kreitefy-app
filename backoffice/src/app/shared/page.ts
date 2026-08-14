export const DEFAULT_PAGE_SIZE = 4;

export interface Page<T> {
  content: T[];
  totalElements: number;
  page: number;
  number: number;
}
export interface PageResponse<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
}
