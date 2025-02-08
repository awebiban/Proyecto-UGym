import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class HorarioService {

  private apiClases = 'http://localhost:8888/api/clases';
  private apiUrl = 'http://localhost:8888/api/horario';

  constructor(private http: HttpClient) { }

  obtenerHorario(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${id}`);
  }

  listadoHorario(): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/listarHorario`);
  }

  modificarHorario(horario: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/modificarHorario/${horario.id}`, horario);
  }

  actualizarImagenHorario(id: number, file: File): Observable<any> {
    const formData = new FormData();
    formData.append('file', file, file.name);

    return this.http.post<any>(`${this.apiUrl}/actualizarImagenHorario/${id}`, formData);
  }

  listadoClases(): Observable<any> {
    return this.http.get<any>(`${this.apiClases}/listar`);
  }

}
