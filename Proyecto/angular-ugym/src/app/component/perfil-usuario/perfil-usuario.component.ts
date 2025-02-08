import { Component, OnInit } from '@angular/core';
import { UsuarioService } from '../../services/usuario.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ReservasService } from '../../services/reservas.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-perfil-usuario',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './perfil-usuario.component.html',
  styleUrls: ['./perfil-usuario.component.css']
})
export class PerfilUsuarioComponent implements OnInit {
  usuario = {
    id: Number(sessionStorage.getItem('id_usuario')) || 0,
    nombre: '',
    email: '',
    imagen: '' as string | null,
    apellido: '',
    fechaNacimiento: '',
    contrasena: ''
  };
  imagenUrl: string | ArrayBuffer | null = null;
  reservas: any[] = [];

  constructor(private usuarioService: UsuarioService, private reservasService: ReservasService) { }

  ngOnInit(): void {
    console.log(sessionStorage.getItem('id_usuario'));
    this.cargarUsuario();
    this.cargarProximasClases(this.usuario.id);

  }

  // Cargar los datos del usuario
  cargarUsuario(): void {
    this.usuarioService.obtenerUsuario(this.usuario.id).subscribe({
      next: (data) => {
        this.usuario = data;
        if (this.usuario.imagen) {
          this.imagenUrl = `http://localhost:8888/api/usuarios/imagen/${this.usuario.imagen}`;  // Ruta para la imagen del usuario
        } else {
          this.imagenUrl = 'assets/imagenes/user.png';  // Ruta para la imagen por defecto
        }
      },
      error: (error) => {
        console.error('Error al cargar los datos del usuario:', error);
        Swal.fire({
          icon: 'error',
          title: 'Error',
          text: 'No se pudieron cargar los datos del usuario',
        });
      }
    });
  }

  // Guardar los cambios del usuario
  guardarCambios(): void {
    if (!this.usuario.nombre || !this.usuario.email) {
      Swal.fire({
        icon: 'error',
        title: 'Campos incompletos',
        text: 'El nombre y el email son obligatorios',
      });
      return;
    }

    const emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    if (!emailPattern.test(this.usuario.email)) {
      Swal.fire({
        icon: 'error',
        title: 'Formato de email incorrecto',
        text: 'Por favor ingrese un email válido',
      });
      return;
    }

    this.usuarioService.guardarUsuario(this.usuario.id, this.usuario).subscribe({
      next: () => {
        Swal.fire({
          icon: 'success',
          title: '¡Éxito!',
          text: '¡Cambios guardados correctamente!',
        });
      },
      error: (error) => {
        console.error('Error al guardar los cambios:', error);
        Swal.fire({
          icon: 'error',
          title: 'Error',
          text: 'No se pudieron guardar los cambios',
        });
      }
    });
  }

  // Cargar las próximas clases del usuario
  cargarProximasClases(id: number): void {
    this.reservasService.obtenerReservas(id).subscribe((data: any[]) => {
      this.reservas = data.map(reserva => ({
        id: reserva.id,
        clase: reserva.horarioDTO.diaSemana,
        instructor: `${reserva.horarioDTO.empleado.nombre} ${reserva.horarioDTO.empleado.apellido}`,
        fecha: reserva.horarioDTO.fecha,
        horaInicio: reserva.horarioDTO.horaInicio,
        horaFin: reserva.horarioDTO.horaFin,
        estado: reserva.estado,
        tipo: reserva.horarioDTO.empleado.rol === 'EMPLEADO' ? 'Privada' : 'General',
      }));
    });
  }

  // Cargar imagen del perfil
  cargarImagen(event: any): void {
    const file = event.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = (e) => {
        this.imagenUrl = e.target?.result ?? null;
      };
      reader.readAsDataURL(file);

      this.usuarioService.actualizarFotoPerfil(this.usuario.id, file).subscribe({
        next: () => {
          Swal.fire({
            icon: 'success',
            title: '¡Éxito!',
            text: 'Foto de perfil actualizada',
          });
          this.cargarUsuario();
        },
        error: (error) => {
          console.error('Error al actualizar la foto de perfil:', error);
          Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'No se pudo actualizar la foto de perfil',
          });
        }
      });
    }
  }

  // Eliminar imagen del perfil
  eliminarImagen(): void {
    this.usuarioService.borrarImagen(this.usuario.id).subscribe({
      next: () => {
        Swal.fire({
          icon: 'success',
          title: '¡Éxito!',
          text: 'Imagen eliminada correctamente',
        });
        this.imagenUrl = 'assets/imagenes/user.png';
        this.usuario.imagen = null;
      },
      error: (error) => {
        console.error('Error al eliminar la imagen:', error);
        Swal.fire({
          icon: 'error',
          title: 'Error',
          text: 'No se pudo eliminar la imagen',
        });
      }
    });
  }
}
