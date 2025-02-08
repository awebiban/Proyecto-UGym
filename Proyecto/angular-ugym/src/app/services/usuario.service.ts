import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {
  private apiUrl = 'http://localhost:8888/api/usuarios';

  constructor(private http: HttpClient) { }

  // Obtener un usuario por su ID
  obtenerUsuario(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${id}`);
  }

  //Listado de Usuarios
  listadoUsuario(): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/listar`);
  }

  // Desactiva un usuario
  desactivarUsuario(id: number, usuario: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/borrar/${id}`, usuario);
  }

  // Activar un usuario
  activarUsuario(id: number, usuario: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/activar/${id}`, usuario);
  }

  // Guardar los cambios del usuario
  guardarUsuario(id: number, usuario: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/modificar/${id}`, usuario);
  }

  //Crear un empleado
  crearEmpleado(usuario: any): Observable<any> {
    console.log(usuario);
    return this.http.post<any>(`${this.apiUrl}/altaEmpleado`, usuario);
  }

  // Actualizar la foto de perfil del usuario
  actualizarFotoPerfil(id: number, file: File): Observable<any> {
    const formData = new FormData();
    formData.append('file', file, file.name);

    return this.http.post<any>(`${this.apiUrl}/actualizarFotoPerfil/${id}`, formData);
  }

  // Eliminar la imagen del usuario
  borrarImagen(id: number): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/borrarImg/${id}`, null);
  }
}
