import { Component, OnInit } from '@angular/core';
import Swal from 'sweetalert2';
import { MensajesService } from '../../services/mensajes.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-mensajes',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './mensajes.component.html',
  styleUrls: ['./mensajes.component.css']
})
export class MensajesComponent implements OnInit {

  mensajes: any[] = []; // Almacena los mensajes del usuario
  usuarioId = sessionStorage.getItem('id_usuario') ? Number(sessionStorage.getItem('id_usuario')) : 0;

  constructor(private mensajeService: MensajesService) { }

  ngOnInit(): void {
    this.cargarMensajesPorUsuario();
  }

  // Cargar mensajes del usuario
  cargarMensajesPorUsuario(): void {
    this.mensajeService.obtenerMensajesPorUsuario(this.usuarioId).subscribe({
      next: (data) => {
        this.mensajes = data;
      },
      error: () => {
        Swal.fire('Error', 'No se pudieron cargar los mensajes', 'error');
      }
    });
  }

  // Ver un mensaje en detalle
  verMensaje(id: number): void {
    this.mensajeService.obtenerMensajePorId(id).subscribe({
      next: (mensaje) => {
        Swal.fire({
          title: 'Mensaje detallado',
          html: `
            <p><strong>Titulo:</strong> ${mensaje.titulo}</p>
            <p><strong>Fecha:</strong> ${mensaje.fechaEnvio}</p>
            <p><strong>Contenido:</strong> ${mensaje.contenido}</p>
          `,
          icon: 'info',
          confirmButtonText: 'Cerrar'
        });
      },
      error: () => {
        Swal.fire('Error', 'No se pudo cargar el mensaje', 'error');
      }
    });
  }

  // Borrar un mensaje por su ID
  borrarMensaje(id: number): void {
    this.mensajeService.borrarMensaje(id).subscribe({
      next: () => {
        this.mensajes = this.mensajes.filter((mensaje) => mensaje.id !== id);
        Swal.fire('¡Borrado!', 'El mensaje ha sido eliminado', 'success');
      },
      error: () => {
        Swal.fire('Error', 'No se pudo borrar el mensaje', 'error');
      }
    });
  }
}
