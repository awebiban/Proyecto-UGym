import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ReservasService {
  private apiUrl = 'http://localhost:8888/api/reservas';
  private apiUsuarios = 'http://localhost:8888/api/usuarios';
  private apiHorarios = 'http://localhost:8888/api/horario';
  private apiBonos = 'http://localhost:8888/api/bonos';
  private apiClases = 'http://localhost:8888/api/clases';

  constructor(private http: HttpClient) { }

  obtenerReservas(id: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/usuario/${id}`);
  }

  borrarReserva(id: number) {
    return this.http.put<string>(`${this.apiUrl}/desactivar/${id}`, null);
  }

  crearReserva(reserva: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/reserva/add`, reserva);
  }

  // Profesores
  obtenerProfesores(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUsuarios}/empleados`);
  }

  // Horarios
  obtenerHorarios(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiHorarios}/listarHorario`);
  }

  crearHorarioPrivado(horarioData: any): Observable<any> {
    return this.http.post(`${this.apiHorarios}/crear`, horarioData);
  }

  // Bonos
  verificarBonos(usuarioId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiBonos}/usuario/${usuarioId}`);
  }

  consumirBono(usuarioId: number, bonoId: number): Observable<any> {
    return this.http.put(`${this.apiBonos}/consumir/${usuarioId}/${bonoId}`, {});
  }

  cearHorarioPrivado(horarioData: any): Observable<any> {
    return this.http.post(`${this.apiHorarios}/crear`, horarioData);
  }

  // Clases
  crearClasePrivada(claseData: any): Observable<any> {
    return this.http.post(`${this.apiClases}/alta`, claseData);
  }
}
