import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ClaseService } from '../../services/clase.service';
import { HorarioService } from '../../services/horario.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-modificar-clase',
  imports: [CommonModule, FormsModule],
  templateUrl: './modificar-clase.component.html',
  styleUrls: ['./modificar-clase.component.css']
})
export class ModificarClaseComponent implements OnInit {
  clase: any = {};
  horario: any = {};
  imagenUrl: string | ArrayBuffer | null = null;
  imagenFile: File | null = null;
  id: number = 0;

  constructor(private route: ActivatedRoute, private claseService: ClaseService, private horarioService: HorarioService, private router: Router) { }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.cargar(Number(id));
    }
  }

  cargar(id: number): void {
    this.claseService.getClaseById(id).subscribe({
      next: (data) => {
        this.clase = data;
        this.horario = data.horario;
        console.log('Horario:', this.horario, 'Clase:', this.clase);
        console.log('Clase cargada:', this.clase);
      },
      error: (err) => {
        console.error('Error al cargar la clase:', err);
        Swal.fire('Error', 'No se pudo cargar la clase', 'error');
      }
    });
  }

  obtenerClase() {
    this.claseService.getClaseById(this.id).subscribe({
      next: (data) => {
        this.clase = data;
        this.horario = data.horario;
        if (data.imagen) {
          this.imagenUrl = data.imagen;
        }
      },
      error: () => {
        Swal.fire('Error', 'No se pudo cargar la clase', 'error');
      }
    });
  }

  cargarImagen(event: any) {
    const file = event.target.files[0];
    if (file) {
      this.imagenFile = file;
      const reader = new FileReader();
      reader.onload = () => {
        this.imagenUrl = reader.result;
      };
      reader.readAsDataURL(file);
    }
  }

  eliminarImagen() {
    this.imagenFile = null;
    this.imagenUrl = null;
  }

  guardarCambios() {
    if (this.imagenFile) {
      this.claseService.actualizarImagenClase(this.clase.id, this.imagenFile).subscribe({
        next: (imagenUrl) => {
          this.clase.imagen = imagenUrl;
          this.actualizarClase();
        },
        error: () => {
          Swal.fire('Error', 'No se pudo actualizar la imagen de la clase', 'error');
        }
      });
    } else {
      this.actualizarClase();
      this.actualizarHorario();
    }
  }

  actualizarClase() {
    this.claseService.modificarClase(this.clase).subscribe({
      next: () => {
        Swal.fire('Éxito', 'Clase modificada con éxito', 'success');
        this.router.navigate(['/lista-horario']);
      },
      error: () => {
        Swal.fire('Error', 'No se pudo modificar la clase', 'error');
      }
    });
  }

  actualizarHorario() {

    console.log(this.horario);

    this.horario.plazasDisponibles = this.clase.capacidadMaxima;

    this.horarioService.modificarHorario(this.horario).subscribe({
      next: () => {
        console.log('Horario modificado con éxito');
      },
      error: (error) => {
        console.error(error);
      }
    });
  }

  cancelar() {
    this.router.navigate(['/lista-horario']);
  }
}