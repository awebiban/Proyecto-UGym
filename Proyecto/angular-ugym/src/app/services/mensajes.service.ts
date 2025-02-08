import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MensajesService {
  private apiUrl = 'http://localhost:8888/api/mensajes';

  constructor(private http: HttpClient) { }

  // Obtener todos los mensajes de un usuario
  obtenerMensajesPorUsuario(usuarioId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/usuario/${usuarioId}`);
  }

  // Obtener un mensaje por su ID
  obtenerMensajePorId(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/ver/${id}`);
  }

  // Borrar un mensaje por su ID
  borrarMensaje(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
