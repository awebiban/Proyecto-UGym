import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { BonosService } from '../../services/bonos.service';
import { ActivatedRoute } from '@angular/router';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-bonos',
  imports: [CommonModule, FormsModule],
  templateUrl: './bonos.component.html',
  styleUrls: ['./bonos.component.css'],
})
export class BonosComponent implements OnInit {
  usuarioId: number = sessionStorage.getItem('id_usuario') ? Number(sessionStorage.getItem('id_usuario')) : 0;
  bonoGeneral: any = { saldo: 0, total: 10, fechaInicio: '', fechaFin: '' };
  bonoPrivado: any = { saldo: 0, total: 10 };
  bonoSeleccionado: string = '';
  clasesAdicionales: number = 0;

  constructor(
    private bonosService: BonosService,
    private route: ActivatedRoute,
  ) { }

  ngOnInit(): void {
    this.obtenerBonos();

    this.route.queryParams.subscribe(params => {
      const status = params['status'];
      const paymentId = params['token'];
      const payerId = params['PayerId'];
      const montoPago = params['montoPago'];
      const tipoBonoId = params['tipoBonoId'];

      if (status === 'success') {
        console.log(params);
        this.bonosService.confirmarPago(paymentId, payerId, this.usuarioId, tipoBonoId, Number(montoPago)).subscribe({
          next: (response) => {
            Swal.fire('Éxito', 'El pago se ha completado con éxito', 'success');
            this.obtenerBonos();
            window.history.replaceState({}, document.title, '/bonos');
          },
          error: (err) => {
            console.log(err);
            Swal.fire('Error', 'No se pudo confirmar el pago', 'error');
          }
        });
      } else if (status === 'cancel') {
        Swal.fire('Cancelado', 'El pago ha sido cancelado', 'error');
      }
    });
  }

  obtenerBonos(): void {
    this.bonosService.obtenerBonosPorUsuario(this.usuarioId).subscribe({
      next: (bonos) => {
        bonos.forEach((bono) => {
          if (bono.tipoBono.id === 1) {
            // Bono General
            this.bonoGeneral = {
              saldo: bono.saldoClases,
              total: bono.tipoBono.duracionDias || 10,
              fechaInicio: bono.fechaInicio,
              fechaFin: bono.fechaFin,
            };
          } else if (bono.tipoBono.id === 2) {
            // Bono Privado
            this.bonoPrivado = {
              saldo: bono.saldoClases,
              total: bono.tipoBono.duracionDias || 10,
            };
          }
        });
      },
      error: (err) => {
        Swal.fire('Error', 'No se pudieron obtener los bonos', 'error');
        console.error('Error al obtener los bonos:', err);
      },
    });
  }

  abrirModal(tipo: string): void {
    this.bonoSeleccionado = tipo;
    this.clasesAdicionales = 0;
    const modal = document.getElementById('modalRecarga');
    if (modal) {
      modal.classList.add('show');
    }
  }

  cerrarModal(): void {
    const modal = document.getElementById('modalRecarga');
    if (modal) {
      modal.classList.remove('show');
    }
  }

  recargarBono(): void {
    // Se calcula el monto basado en el bono seleccionado
    const amount = this.bonoSeleccionado === 'General' ? 30 : this.clasesAdicionales * 10;

    // Validación de monto
    if (!amount || (this.bonoSeleccionado === 'Privado' && this.clasesAdicionales <= 0)) {
      Swal.fire('Error', 'Por favor, selecciona una cantidad válida.', 'error');
      return;
    }

    // Llamada al servicio de recarga con el monto calculado
    this.bonosService.recargarBono(this.usuarioId, this.bonoSeleccionado, this.clasesAdicionales).subscribe({
      next: (response) => {
        console.log('Respuesta del backend:', response);
        if (response && response.approvalLink) {
          window.location.href = response.approvalLink;
        } else {
          Swal.fire('Error', 'No se pudo iniciar la recarga.', 'error');
        }
      },
      error: (err) => {
        console.error('Error al recargar el bono:', err);
        Swal.fire('Error', 'No se pudo iniciar la recarga.', 'error');
      },
    });
  }

}
