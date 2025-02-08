import { Component, OnInit } from '@angular/core';
import { HeaderService } from '../../services/header.service';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { UsuarioService } from '../../services/usuario.service';

/*REVISAR Y CAMBIAR CODIGO DE OBTENCION DE IMAGEN*/



@Component({
  selector: 'app-header',
  imports: [CommonModule],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css',
})
export class HeaderComponent implements OnInit {
  usuario: any = {
    id: 3
  };

  userPhotoUrl: String = 'assets/imagenes/';
  idUsuario: number = Number(sessionStorage.getItem('id_usuario'));


  constructor(private router: Router, private headerService: HeaderService, private usuarioService: UsuarioService) { }

  ngOnInit(): void {
    this.obtenerUsuario(this.usuarioService);
  }
  perfil(): void {
    this.router.navigate(['/perfil-usuario']);
  }

  mensajes(): void {
    this.router.navigate(['/mensajes']);
  }

  inicio(): void {
    this.router.navigate(['/horario']);
  }
  obtenerUsuario(usuarioService: UsuarioService) {
    usuarioService.obtenerUsuario(this.idUsuario).subscribe ({
      next: (data) => {
        if (data.imagen) {
          console.log(data.imagen)
          this.userPhotoUrl = `http://localhost:8888/api/usuarios/imagen/${data.imagen}`; // Ruta para la imagen del usuario
          console.log()
        }
        else {
          this.userPhotoUrl = "assets/imagenes/user.png";
        }
      },
      error: (e) => {
        console.error("Error obteniendo usuario " + e)
      }
    })
  }
}


