import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { UsuarioService } from '../../services/usuario.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-lista-usuarios',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './lista-usuarios.component.html',
  styleUrls: ['./lista-usuarios.component.css'],
})
export class ListaUsuariosComponent implements OnInit {
  
  usuario: any[] = [];  
  usuariosFiltrados: any[] = [];  
  error: string = '';  
  pageSize: number = 6; 
  currentPage: number = 1; 
  rolSeleccionado: string = '';  

  constructor(private UsuarioService: UsuarioService, private router: Router) { }

  ngOnInit(): void {
    this.cargarUsuarios(); 
  }

  cargarUsuarios() {
    this.UsuarioService.listadoUsuario().subscribe({
      next: (data) => {
        this.usuario = data; 
        this.usuariosFiltrados = data;
      },
      error: () => {
        Swal.fire('Error', 'No se pudieron cargar los usuarios', 'error');
      }
    });
  }

  get paginatedData() {
    const start = (this.currentPage - 1) * this.pageSize;
    const end = start + this.pageSize;
    return this.usuariosFiltrados.slice(start, end); 
  }

  setPage(page: number) {
    if (page >= 1 && page <= this.totalPages) {
      this.currentPage = page;
    }
  }

  get totalPages() {
    return Math.ceil(this.usuariosFiltrados.length / this.pageSize);
  }

  mostrarEmpleados() {
    this.rolSeleccionado = 'empleado';
    this.usuariosFiltrados = this.usuario.filter(usuario => usuario.rol === 'EMPLEADO');
    this.currentPage = 1;  
  }

  mostrarUsuarios() {
    this.rolSeleccionado = 'usuario';
    this.usuariosFiltrados = this.usuario.filter(usuario => usuario.rol === 'USUARIO');
    this.currentPage = 1;  
  }

  editarUsuario(id: number) {
    this.router.navigate(['/editarUsuario', id]);
  }

  eliminarUsuario(id: number) {
    Swal.fire({
      title: '¿Estás seguro?',
      text: "¡Este cambio no se puede deshacer!",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Sí, desactivar',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if (result.isConfirmed) {
        const usuario = this.usuario.find(user => user.id === id);
        if (usuario) {
          usuario.activo = false;
          this.UsuarioService.desactivarUsuario(id, usuario).subscribe({
            next: () => {
              Swal.fire('Error', 'No se pudo desactivar el usuario', 'error');
              this.cargarUsuarios();  
            },
            error: () => {
              Swal.fire('Desactivado', 'El usuario ha sido desactivado', 'success');
              
            }
          });
        }
      }
    });
  }

  retornarUsuario(id: number) {
    Swal.fire({
      title: '¿Estás seguro?',
      text: "¡Este cambio no se puede deshacer!",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Sí, activar',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if (result.isConfirmed) {
        const usuario = this.usuario.find(user => user.id === id);
        if (usuario) {
          usuario.activo = false;
          this.UsuarioService.activarUsuario(id, usuario).subscribe({
            next: () => {
              Swal.fire('Error', 'No se pudo activar el usuario', 'error');
              this.cargarUsuarios();  
            },
            error: () => {
              
              Swal.fire('Activado', 'El usuario ha sido activado de nuevo', 'success');
            }
          });
        }
      }
    });
  }
}
