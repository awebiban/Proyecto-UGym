import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})

export class LoginService {

  private apiUrl = 'http://localhost:8888/api/auth/login';

  constructor(private http: HttpClient) { }

  login(email: string, contraseña: string): Observable<any> {
    return this.http.post<string>(`${this.apiUrl}?email=${email}&contraseña=${contraseña}`, {}).pipe(
      catchError(err => {
        throw err;
      })
    );
  }

  // Verificar si el usuario está autenticado
  isAuthenticated(): boolean {
    return sessionStorage.getItem('id_usuario') !== null;
  }

  setAuthenticated(userId: string): void {
    sessionStorage.setItem('isLoggedIn', 'true');
    sessionStorage.setItem('id_usuario', userId);
  }

  logout(): void {
    sessionStorage.removeItem('isLoggedIn');
    sessionStorage.removeItem('id_usuario');
  }

}
