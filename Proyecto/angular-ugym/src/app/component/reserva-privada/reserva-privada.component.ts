import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ReservasService } from '../../services/reservas.service';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-reserva-privada',
  imports: [CommonModule, FormsModule, ReactiveFormsModule],
  templateUrl: './reserva-privada.component.html',
  styleUrls: ['./reserva-privada.component.css'],
})
export class ReservaPrivadaComponent implements OnInit {
  reservaForm: FormGroup;
  profesores: any[] = [];
  horarios: any[] = [];
  horariosFiltrados: any[] = [];
  bonoPrivado: { id: number; saldo: number } | null = null;

  constructor(
    private fb: FormBuilder,
    private reservasService: ReservasService,
    private router: Router
  ) {
    this.reservaForm = this.fb.group({
      usuarioId: [{ value: '', disabled: true }, Validators.required],
      profesorId: ['', Validators.required],
      horarioId: ['', Validators.required],
      fechaReserva: ['', Validators.required],
      diaSemana: ['', Validators.required],
      horaReserva: ['', Validators.required],
    });
  }

  ngOnInit(): void {
    this.cargarUsuarioId();
    this.cargarProfesores();
    this.generarHorarios();
  }

  cargarUsuarioId(): void {
    const usuarioId = sessionStorage.getItem('id_usuario');
    if (usuarioId) {
      this.reservaForm.patchValue({ usuarioId });
      console.log(usuarioId);
    } else {
      Swal.fire('Error', 'No se encontró un ID de usuario en la sesión.', 'error');
    }
  }

  cargarProfesores(): void {
    this.reservasService.obtenerProfesores().subscribe({
      next: (data) => {
        this.profesores = data;
      },
      error: (err) => {
        Swal.fire('Error', 'No se pudieron cargar los profesores.', 'error');
        console.error('Error al cargar los profesores:', err);
      },
    });
  }

  generarHorarios(): void {
    const dias = ['Lunes', 'Martes', 'Miércoles', 'Jueves', 'Viernes', 'Sábado', 'Domingo'];
    this.horarios = [];
  
    for (let i = 8; i < 18; i++) {
      for (const dia of dias) {
        const horaInicio = `${i}`.padStart(2, '0') + ':00';
        const horaFin = `${i + 1}`.padStart(2, '0') + ':00';
  
        this.horarios.push({
          horaInicio: horaInicio,
          horaFin: horaFin,
          diaSemana: dia,
        });
      }
    }
  }

  filtrarHorariosPorDia(): void {
    const diaSeleccionado = this.reservaForm.get('diaSemana')?.value;
    if (diaSeleccionado) {
      this.horariosFiltrados = this.horarios.filter(
        (horario) => horario.diaSemana === diaSeleccionado
      );
    } else {
      this.horariosFiltrados = [];
    }
  }

  obtenerBonos(): void {
    const usuarioId = this.reservaForm.get('usuarioId')?.value;

    if (!usuarioId) {
      Swal.fire('Error', 'Debes ingresar un ID de usuario válido.', 'error');
      return;
    }

    this.reservasService.verificarBonos(usuarioId).subscribe({
      next: (bonos) => {
        const bonoPrivado = bonos.find((bono) => bono.tipoBono.id === 2);

        if (bonoPrivado && bonoPrivado.saldoClases > 0) {
          this.bonoPrivado = { id: bonoPrivado.id, saldo: bonoPrivado.saldoClases };
          Swal.fire('Bonos Verificados', 'Tienes bonos privados disponibles para usar.', 'success');
        } else {
          this.bonoPrivado = null;
          Swal.fire('Sin Bonos', 'No tienes bonos privados suficientes para realizar esta reserva.', 'warning');
        }
      },
      error: (err) => {
        Swal.fire('Error', 'No se pudieron obtener los bonos.', 'error');
        console.error('Error al obtener los bonos:', err);
      },
    });
  }


  realizarReserva(): void {
    const usuarioId = this.reservaForm.get('usuarioId')?.value;
  
    console.log(this.reservaForm.value);
  
    if (!usuarioId) {
      Swal.fire('Error', 'El ID de usuario es inválido', 'error');
      return;
    }
  
    if (this.reservaForm && this.bonoPrivado) {
      const reservaData = {
        ...this.reservaForm.value,
        bonoPrivado: this.bonoPrivado.saldo,
        usuarioDTO: {
          id: this.reservaForm.get('usuarioId')?.value,
        },
      };

      const horarioData = {
        empleado: { id: this.reservaForm.get('profesorId')?.value },
        horaInicio: this.reservaForm.get('horaReserva')?.value,
        horaFin: `${parseInt(this.reservaForm.get('horaReserva')?.value.split(':')[0]) + 1}:00`,
        duracionMinutos: 60,
        diaSemana: this.reservaForm.get('diaSemana')?.value,
        fecha: this.reservaForm.get('fechaReserva')?.value,
        plazasDisponibles: 1,
      };
      console.log(horarioData);
  
      this.reservasService.crearHorarioPrivado(horarioData).subscribe({
        next: (horarioCreado) => {
          const claseData = {
            nombreClase: 'Clase Privada',
            descripcion: 'Clase privada de usuario',
            categoria: 'Privada',
            tipoClase: 2,
            horario: { id: horarioCreado.id },
            capacidadMaxima: 1,
            duracionMaxima: 60,
          };
  
          console.log("crearClase");
  
          this.reservasService.crearClasePrivada(claseData).subscribe({
            next: () => {
              this.reservasService
                .consumirBono(usuarioId, this.bonoPrivado!.id)
                .subscribe({
                  next: () => {
                    const reserva = {
                      usuarioDTO: {id: usuarioId},
                      horarioDTO: {id : horarioCreado.id},
                      fechaReserva: this.reservaForm.get('fechaReserva')?.value,
                      estado: 1,
                    };
  
                    this.reservasService.crearReserva(reserva).subscribe({
                      next: () => {
                        Swal.fire('Reserva Exitosa', 'La reserva se ha realizado correctamente', 'success');
                        this.reservaForm.reset();
                        this.bonoPrivado = null;
                        this.router.navigate(['/reservas']);
                      },
                      error: (error) => {
                        Swal.fire('Error', 'Hubo un problema al realizar la reserva', 'error');
                      },
                    });
                  },
                  error: (error) => {
                    Swal.fire('Error', 'Hubo un problema al consumir el bono', 'error');
                  }
                });
            },
            error: (error) => {
              Swal.fire('Error', 'Hubo un problema al crear la clase privada', 'error');
            }
          });
        },
        error: (error) => {
          Swal.fire('Error', 'Hubo un problema al crear el horario', 'error');
        }
      });
    } else {
      Swal.fire('Error', 'Completa todos los campos o verifica tus bonos', 'error');
    }
  }  
}
