import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class UploadFileService {
  private baseUrl = '/api';

  constructor(private http: HttpClient) {}

  deleteFile(fileId: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/deleteFile/${fileId}`);
  }
}
