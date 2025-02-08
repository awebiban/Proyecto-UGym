import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
import { HorarioService } from '../../services/horario.service';
import { ReservasService } from '../../services/reservas.service';
import { UsuarioService } from '../../services/usuario.service';
import { BonosService } from '../../services/bonos.service';

@Component({
  selector: 'app-horario',
  imports: [CommonModule],
  templateUrl: './horario.component.html',
  styleUrl: './horario.component.css'
})
export class HorarioComponent implements OnInit {

  clases: any[] = [];
  clasesPorDia: any[] = [];
  listaReservas: any[] = [];
  reservaRealizada: boolean = false;
  urlImagenBase: string = '../../../assets/imagenes/';
  idUsuario: number = Number(sessionStorage.getItem('id_usuario'));

  diaSeleccionado: string = '';


  constructor(
    private horarioService: HorarioService,
    private router: Router,
    private reservaService: ReservasService,
    private usuarioService: UsuarioService,
    private bonosService: BonosService
  ) { }

  ngOnInit(): void {
    this.cargarClasesDesdeAPI();
  }

  cargarClasesDesdeAPI(): void {
    this.horarioService.listadoClases().subscribe({
      next: (data) => {
        this.clases = data;
        this.obtenerReservasUsuario();
      },
      error: () => Swal.fire('Error', 'No se pudieron cargar los horarios', 'error')
    });
  }

  obtenerReservasUsuario(): void {
    this.reservaService.obtenerReservas(this.idUsuario).subscribe({
      next: (data) => {
        this.listaReservas = data;
        this.marcarReservasRealizadas();
      },
      error: () => Swal.fire('Error', 'No se pudieron cargar las reservas', 'error')
    });
  }

  marcarReservasRealizadas(): void {
    this.listaReservas.forEach(reserva => {
      this.clases.forEach(clase => {
        if (reserva.horarioDTO.id === clase.horario.id && reserva.estado === '1') {
          clase.reservaRealizada = true;
        }
      });
    });
  }

  cargarClases(dia: string): void {
    this.clasesPorDia = this.clases.filter(c => c.horario.diaSemana === this.obtenerNombreDia(dia));
  }




  obtenerNombreDia(dia: string): string {
    const dias: { [key: string]: string } = { 'L': 'Lunes', 'M': 'Martes', 'X': 'Miércoles', 'J': 'Jueves', 'V': 'Viernes', 'S': 'Sábado', 'D': 'Domingo' };
    return dias[dia] || '';
  }

  reservar(horario: any, clase: any): void {
    if (clase.plazasDisponibles <= 0) {
      Swal.fire('Atención', 'No hay plazas disponibles para esta clase.', 'warning');
      return;
    }

    this.bonosService.obtenerBonosPorUsuario(this.idUsuario).subscribe({
      next: (bonos) => {
        const bonoActivo = bonos.find(b => b.tipoBono.nombre === 'General' && b.fechaFin >= new Date().toISOString().split('T')[0]);

        if (!bonoActivo) {
          Swal.fire('Atención', 'No tienes un bono mensual activo o está caducado.', 'warning');
          return;
        }

        this.usuarioService.obtenerUsuario(this.idUsuario).subscribe({
          next: (usuario) => {
            const reserva = {
              usuarioDTO: usuario,
              horarioDTO: horario,
              fechaReserva: new Date().toISOString().split('T')[0],
              estado: "1"
            };

            this.reservaService.crearReserva(reserva).subscribe({
              next: () => {
                clase.plazasDisponibles -= 1;
                clase.reservaRealizada = true;

                this.horarioService.modificarHorario(horario).subscribe({
                  next: () => Swal.fire('Reserva exitosa!', 'Tu reserva se ha realizado con éxito.', 'success').then(() => window.location.reload()),
                  error: (error) => console.error('Error al modificar el horario:', error)
                });
              },
              error: (error) => Swal.fire('Error', 'Hubo un error en la reserva. Intente nuevamente.', 'error')
            });
          }
        });
      }
    });
  }

  recargar(): void {
    this.router.navigate(['/horario']);
  }

  reservaPrivada(): void {
    this.router.navigate(['/reserva-privada']);
  }
}
