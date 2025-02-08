import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { startWith } from 'rxjs';

@Component({
  selector: 'app-sidebar-fija',
  imports: [CommonModule],
  templateUrl: './sidebar-fija.component.html',
  styleUrls: ['./sidebar-fija.component.css']
})
export class SidebarFijaComponent {

  isOpen = false;
  isSidebarEnabled = false;

  constructor(private router: Router) {
    this.router.events.subscribe(() => {
      const currentRoute = this.router.url;
      this.isSidebarEnabled = !['/login', '/registro', '/editarUsuario/:id'].includes(currentRoute);
    });
  }

  isSidebarVisible(): boolean {
    const currentRoute = this.router.url;

    return ['/altaEmpleado', '/editarEmpleado', '/listadoUsuario'].includes(currentRoute);

  }

  inicio(): void {
    this.router.navigate(['/']);
  }

  verHorarios(): void {
    this.router.navigate(['/horario']);
  }

  listaUsuarios(): void {
    this.router.navigate(['/listadoUsuario']);
  }

  altaEmpleado(): void {
    this.router.navigate(['/altaEmpleado']);
  }

}
