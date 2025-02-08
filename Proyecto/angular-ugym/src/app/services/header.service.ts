import { Injectable } from '@angular/core';
import { UsuarioService } from './usuario.service';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class HeaderService {
  
  private urlUsersPhotoFolder = '../assets/user.png'; //CAMBIAR A FUTURO CUANDO SEPAMOS CUAL ES LA CARPETA
  private defaultUserPhoto = 'user.png'; //FOTO POR DEFECTO POR SI NO SE ENCUENTRA LA IMAGEN DEL USUARIO O NO TIENE NINGUNA

   // Usamos BehaviorSubject para que los componentes puedan suscribirse a los cambios del estado.
   private showHeaderSubject = new BehaviorSubject<boolean>(true); // Valor inicial: true (mostrar header)
   showHeader$ = this.showHeaderSubject.asObservable();  // Observable que los componentes pueden suscribirse

  usuario: any;

  constructor(private usuarioService: UsuarioService) { }

  getUserImage(idUsuario: any) {
    this.usuario = this.usuarioService.obtenerUsuario(idUsuario);
    if (this.usuario) {
      console.log("HeaderService - getUserImage - DEVOLVEMOS EL NOMBRE DE LA IMAGEN DEL USUARIO");
      return this.usuario.imagen; // IMAGEN CUSTOM
    }
    console.log("HeaderService - getUserImage - DEVOLVEMOS LA IMAGEN POR DEFECTO");
    return this.defaultUserPhoto; //IMAGEN POR DEFECTO
  }
  setShowHeader(show: boolean) {
    this.showHeaderSubject.next(show);
  }
}
