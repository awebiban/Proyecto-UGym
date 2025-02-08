import { Component, OnInit, ViewChild } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { RegistroService } from '../../services/registro.service';
import Swal from 'sweetalert2';
import { Router } from '@angular/router';

@Component({
  selector: 'app-registro',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './registro.component.html',
  styleUrls: ['./registro.component.css'],
})
export class RegistroComponent implements OnInit {
  @ViewChild('registroForm') registroForm!: NgForm;

  usuario: any = {
    nombre: '',
    apellidos: '',
    email: '',
    contrasena: '',
    repetirContrasena: ''
  };

  imagenUrl: File | null = null;
  formaData: FormData = new FormData();

  constructor(private router: Router, private registroService: RegistroService) { }

  ngOnInit(): void { }

  registro() {
    if (this.registroForm.invalid || this.usuario.contrasena !== this.usuario.repetirContrasena) {
      Swal.fire('Error', 'Por favor, completa correctamente todos los campos.', 'error');
      return;
    }

    // Crear FormData para enviar al backend
    this.formaData = new FormData();
    this.formaData.append('nombre', this.usuario.nombre);
    this.formaData.append('email', this.usuario.email);
    this.formaData.append('apellido', this.usuario.apellidos);
    this.formaData.append('password', this.usuario.contrasena);
    this.formaData.append('rol', 'USUARIO');

    if (this.imagenUrl) {
      this.formaData.append('file', this.imagenUrl);
    }

    this.registroService.registro(this.formaData).subscribe({
      next: (response) => {
        Swal.fire('Registro exitoso', 'El usuario fue registrado correctamente.', 'success');
        this.router.navigate(['/login']);
      },
      error: (error) => {
        Swal.fire('Error', 'Hubo un error en el registro. Intente nuevamente.', 'error');
      }
    });
  }

  onFileChange(event: any) {
    const file = event.target.files[0];
    if (file) {
      this.imagenUrl = file;
    }
  }

  landing(): void {
    this.router.navigate(['/']);
  }
}
