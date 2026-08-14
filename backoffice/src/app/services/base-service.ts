import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environment/environment';
import { PageResponse } from '../shared/page';

@Injectable({
  providedIn: 'root',
})
export abstract class BaseService<T> {
  protected readonly httpClient: HttpClient = inject(HttpClient);

  protected abstract readonly listEndpoint: string;
  protected abstract readonly itemEndpoint: string;

  protected get listUrl(): string {
    return `${environment.baseUrl}${this.listEndpoint}`;
  }

  protected get itemUrl(): string {
    return `${environment.baseUrl}${this.itemEndpoint}`;
  }

  findAll(page: number = 0, size: number = 0): Observable<PageResponse<T>> {
    const url = `${this.listUrl}?page=${page}&size=${size}`;
    return this.httpClient.get<PageResponse<T>>(url);
  }

  findAllUnpaged(): Observable<PageResponse<T>> {
    return this.httpClient.get<PageResponse<T>>(this.listUrl + '/all/unpaged');
  }

  create(item: T): Observable<T[]> {
    return this.httpClient.post<T[]>(this.listUrl, item);
  }

  findById(id: number): Observable<T> {
    return this.httpClient.get<T>(`${this.itemUrl}/${id}`);
  }

  update(id: number,item: T): Observable<T[]> {
    return this.httpClient.put<T[]>(`${this.itemUrl}/${id}`, item);
  }

  delete(id: number) {
    return this.httpClient.delete<void>(`${this.itemUrl}/${id}`);
  }
}
