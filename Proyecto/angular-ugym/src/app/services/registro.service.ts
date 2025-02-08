import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RegistroService {
  private apiUrl = 'http://localhost:8888/api/auth/registro';
  
  constructor(private http: HttpClient) { }

  registro(formData: FormData): Observable<any> {
    return this.http.post<any>(this.apiUrl, formData);
  }
} 
