import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { UsuarioService } from '../../services/usuario.service';
import { FormsModule } from '@angular/forms';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-alta-empleado',
  imports: [CommonModule, FormsModule],
  templateUrl: './alta-empleado.component.html',
  styleUrl: './alta-empleado.component.css'
})
export class AltaEmpleadoComponent {
  usuario = {
    nombre: '',
    apellido: '',
    email: '',
    password: '',
    confirmPassword: '',
    rol: 'EMPLEADO',
  };

  constructor(private usuarioService: UsuarioService) { }

  guardarEmpleado() {

    if (this.usuario.password !== this.usuario.confirmPassword) {
      Swal.fire('Error', 'Las contraseñas no coinciden', 'error');
      return;
    }
    

    this.usuarioService.crearEmpleado(this.usuario).subscribe({
      next: () => {
        Swal.fire('Éxito', 'Empleado registrado correctamente', 'success');
        this.limpiarFormulario();
      },
      error: (err) => {
        Swal.fire('Error', 'No se pudo guardar el empleado', 'error');
        console.error(err);
      }
    });
  }

  limpiarFormulario() {
    this.usuario = {
      nombre: '',
      apellido: '',
      email: '',
      password: '',
      confirmPassword: '',
      rol: 'EMPLEADO',
    };
  }
}
