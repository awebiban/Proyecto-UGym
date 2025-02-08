import { Component, OnInit } from '@angular/core';
import Swal from 'sweetalert2';
import { ReservasService } from '../../services/reservas.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-reservas',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './reservas.component.html',
  styleUrls: ['./reservas.component.css']
})
export class ReservasComponent implements OnInit {

  reservas: any[] = [];
  reservasFiltradas: any[] = [];
  mostrarGenerales: boolean = true;
  mostrarPrivadas: boolean = true;
  usuarioId = Number(sessionStorage.getItem('id_usuario')) || 0;

  constructor(private reservasService: ReservasService) { }

  ngOnInit(): void {
    this.cargarReservas(this.usuarioId);
  }

  cargarReservas(id: number): void {
    this.reservasService.obtenerReservas(id).subscribe((data: any[]) => {
      this.reservas = data
        .map(reserva => ({
          id: reserva.id,
          clase: reserva.horarioDTO.diaSemana,
          instructor: `${reserva.horarioDTO.empleado.nombre} ${reserva.horarioDTO.empleado.apellido}`,
          fecha: reserva.horarioDTO.fecha,
          horaInicio: reserva.horarioDTO.horaInicio,
          horaFin: reserva.horarioDTO.horaFin,
          estado: reserva.estado,
          tipo: reserva.horarioDTO.empleado.rol === 'EMPLEADO' ? 'Privada' : 'General',
        }))
        .filter(reserva => reserva.estado === "1");
      this.filtrarClases();
    });
  }

  filtrarClases(): void {
    this.reservasFiltradas = this.reservas.filter(reserva =>
      ((this.mostrarGenerales && reserva.tipo === 'General') ||
      (this.mostrarPrivadas && reserva.tipo === 'Privada'))
    );
  }

  borrarReserva(id: number): void {
    Swal.fire({
      title: '¿Estás seguro?',
      text: 'Esta acción no se puede deshacer.',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Sí, borrar',
      cancelButtonText: 'Cancelar',
    }).then((result) => {
      if (result.isConfirmed) {
        this.reservasService.borrarReserva(id).subscribe({
          next: (response) => {
            if (response === 'OK') {
              Swal.fire('Cancelada', 'La reserva ha sido cancelada.', 'success');
              this.cargarReservas(this.usuarioId);
            } else {
              Swal.fire('Error', 'Hubo un problema al cancelar la reserva.', 'error');
            }
          },
          error: (error) => {
            console.error(error);
            Swal.fire('Error', 'Hubo un problema al cancelar la reserva.', 'error');
          }
        });
      }
    });
  }

  editarReserva(reserva: any): void {
    Swal.fire('Editar Reserva', `Editar reserva para ${reserva.clase}`, 'info');
  }
}
