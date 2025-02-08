import { CommonModule } from '@angular/common';
import { Component, HostListener, OnInit } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';
import { LoginService } from '../../services/login.service';

@Component({
  selector: 'app-sidebar',
  imports: [CommonModule],
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent implements OnInit {

  isOpen = false;
  isSidebarEnabled = true;
  isAuthenticated = false;

  constructor(private router: Router, private loginService: LoginService) {
    this.router.events.subscribe((event) => {
      if (event instanceof NavigationEnd) {
        const currentRoute = event.url;
        // direcciones donde NO debe aparecer el sidebar
        this.isSidebarEnabled = !(currentRoute === '/login' || currentRoute === '/registro' || 
          currentRoute === '/listadoUsuario' || currentRoute === '/altaEmpleado' || currentRoute.startsWith('/editarUsuario'));
        if (currentRoute === '/') {
          this.isSidebarEnabled = false;
        }
      }
    });
  }

  ngOnInit() {
    this.isAuthenticated = this.loginService.isAuthenticated();
    // this.checkIfSidebarShouldBeEnabled();
  }

  /*
  checkIfSidebarShouldBeEnabled() {
    const currentRoute = this.router.url;
    if (currentRoute === '/login' || currentRoute === '/registro' || currentRoute === '/listadoUsuario' || currentRoute === '/') {
      this.isSidebarEnabled = false;
    } else if (currentRoute === '/') {
      this.isSidebarEnabled = false;
    }
  }
  */

  toggleSidebar() {
    this.isOpen = !this.isOpen;
  }

  @HostListener('document:click', ['$event'])
  onDocumentClick(event: MouseEvent) {
    const clickedElement = event.target as HTMLElement;

    if (this.isOpen && !clickedElement.closest('.sidebar') && !clickedElement.closest('.toggle-btn')) {
      this.isOpen = false;
    }
  }

  miPerfil(): void {
    this.router.navigate(['/perfil-usuario']);
    this.isOpen = false;
  }

  misMsjs(): void {
    this.router.navigate(['/mensajes']);
    this.isOpen = false;
  }

  misReservas(): void {
    this.router.navigate(['/reservas']);
    this.isOpen = false;
  }

  misBonos(): void {
    this.router.navigate(['/bonos']);
    this.isOpen = false;
  }

  logout() {
    this.loginService.logout();
    this.isAuthenticated = false;
    this.router.navigate(['/login']);
  }

}