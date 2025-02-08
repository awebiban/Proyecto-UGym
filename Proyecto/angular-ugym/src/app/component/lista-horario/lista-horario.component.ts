import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { HorarioService } from '../../services/horario.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-lista-horario',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './lista-horario.component.html',
  styleUrls: ['./lista-horario.component.css']
})
export class ListaHorarioComponent {

  horario: any[] = [];  
  horariosFiltrados: any[] = [];  
  error: string = '';   
  diaSeleccionado: string = ''; 

  constructor(private HorarioService: HorarioService, private router: Router) { }

  ngOnInit(): void {
    this.cargarHorarios(); 
  }

  cargarHorarios() {
    this.HorarioService.listadoHorario().subscribe({
      next: (data) => {
        console.log(data);
        this.horario = data; 
        this.horariosFiltrados = data;
      },
      error: () => {
        Swal.fire('Error', 'No se pudieron cargar los horarios', 'error');
      }
    });
  }

  mostrarDia(dia: string) {
    this.diaSeleccionado = dia;
    this.horariosFiltrados = this.horario.filter(horario => 
      horario.diaSemana && horario.diaSemana.toLowerCase() === dia.toLowerCase()
    );
  }

  mostrarTodos() {
    this.diaSeleccionado = '';
    this.horariosFiltrados = this.horario;
  }

  editarClase(id: number) {
    console.log('ID de la clase a editar:', id);
    this.router.navigate(['/modificar-clase', id]);
  }

}