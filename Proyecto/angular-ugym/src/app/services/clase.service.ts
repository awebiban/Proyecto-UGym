import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ClaseService {
  private apiUrl = 'http://localhost:8888/api/clases';

  constructor(private http: HttpClient) {}

  getClaseById(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${id}`);
  }

  modificarClase(clase: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/modificar`, clase);
  }

  actualizarPlazasDisponibles(id_horario: number, plazasDisponibles: number): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/actualizarPlazasDisponibles/${id_horario}`, { plazasDisponibles });
  }

  actualizarImagenClase(id: number, file: File): Observable<any> {
    const formData = new FormData();
    formData.append('file', file, file.name);
    return this.http.post<any>(`${this.apiUrl}/actualizarImagenClase/${id}`, formData);
  }
}
