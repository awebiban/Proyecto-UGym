import { Component, OnInit } from '@angular/core';
import { UsuarioService } from '../../services/usuario.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-editar-usuario',
  imports: [FormsModule, CommonModule],
  templateUrl: './editar-usuario.component.html',
  styleUrl: './editar-usuario.component.css'
})
export class EditarUsuarioComponent {

  usuario = {
    id: 0,
    nombre: '',
    email: '',
    imagen: '' as string | null,
    apellido: '',
    fechaNacimiento: '',
    contrasena: ''
  };

  imagenUrl: string | ArrayBuffer | null = null;

  constructor(private usuarioService: UsuarioService, private route: ActivatedRoute, private router: Router) { }

  ngOnInit(): void {
      this.route.paramMap.subscribe(params => {
      const idParam = params.get('id');
      const id = idParam ? +idParam : 0;
      this.usuario.id = id;

      this.cargarUsuario();
    });
  }

  cargarUsuario(): void {
    this.usuarioService.obtenerUsuario(this.usuario.id).subscribe({
      next: (data) => {
        this.usuario = data;
        if (this.usuario.imagen) {
          this.imagenUrl = `http://localhost:8888/uploads/fotos_perfil/${this.usuario.imagen}`;
        } else {
          this.imagenUrl = 'assets/imagenes/user.png';
        }
      },
      error: (error) => {
        console.error('Error al cargar los datos del usuario:', error);
        Swal.fire({
          icon: 'error',
          title: 'Error',
          text: 'No se pudieron cargar los datos del usuario.',
        });
      }
    });
  }

  guardarCambios(): void {
    if (!this.usuario.nombre || !this.usuario.email) {
      Swal.fire({
        icon: 'error',
        title: 'Campos incompletos',
        text: 'El nombre y el email son obligatorios.',
      });
      return;
    }
  
    const emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    if (!emailPattern.test(this.usuario.email)) {
      Swal.fire({
        icon: 'error',
        title: 'Formato de email incorrecto',
        text: 'Por favor ingrese un email válido.',
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
        this.router.navigate(['/listadoUsuario']);
      },
      error: (error) => {
        console.error('Error al guardar los cambios:', error);
        Swal.fire({
          icon: 'error',
          title: 'Error',
          text: 'No se pudieron guardar los cambios.',
        });
      }
    });
  }

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
