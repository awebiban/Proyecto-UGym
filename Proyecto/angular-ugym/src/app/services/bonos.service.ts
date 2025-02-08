import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { switchMap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root',
})
export class BonosService {
  private apiUrl = 'http://localhost:8888/api/bonos';
  private paymentApiUrl = 'http://localhost:8888/api/payments';

  constructor(private http: HttpClient) { }

  confirmarPago(paymentId: string, payerId: string, usuarioId: number, tipoBonoId: number, montoPago: number): Observable<any> {
    const params = { paymentId, usuarioId, tipoBonoId, montoPago };

    return this.http.get<any>(`${this.paymentApiUrl}/confirm-payment`, { params }).pipe(
      switchMap((response) => {
        if (response.status === 'success') {
          return this.obtenerBonosPorUsuario(usuarioId);
        } else {
          throw new Error(response.message || 'Pago no verificado');
        }
      })
    );
  }

  // Obtener los bonos de un usuario por su ID
  obtenerBonosPorUsuario(usuarioId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/usuario/${usuarioId}`);
  }

  // Recargar saldo del bono general o privado
  recargarBono(usuarioId: number, tipoBono: string, clasesAdicionales: number): Observable<any> {
    let amount = tipoBono === 'General' ? 30 : clasesAdicionales * 10;


    const payload = {
      amount: amount,
      usuarioId: usuarioId,
      tipoBonoId: tipoBono === 'General' ? 1 : 2,
    };

    console.log(payload);

    return this.http.post<any>(`${this.paymentApiUrl}/pay`, payload);
  }

}
