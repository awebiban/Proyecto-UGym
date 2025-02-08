import { Component, OnInit } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { SidebarFijaComponent } from './component/sidebar-fija/sidebar-fija.component';
import { HeaderComponent } from './component/header/header.component';
import { CommonModule } from '@angular/common';
import { Router, NavigationEnd } from '@angular/router';
import { filter } from 'rxjs/operators';
import { HeaderService } from './services/header.service';
import { FooterComponent } from './component/footer/footer.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    RouterOutlet,
    SidebarFijaComponent,
    HeaderComponent,
    CommonModule,
    FooterComponent
  ],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent implements OnInit {
  showHeader = true;
  title = 'angular-ugym';

  // Definir las rutas en las que no se debe mostrar el header
  private routesWithoutHeader: string[] = [
    '/login',
    '/registro',
    '/bonos/confirm-payment',
    '/listadoUsuario',
    '/altaEmpleado',
    '/',
    '',
    '/editarUsuario/:id'
  ];

  constructor(private router: Router, private headerService: HeaderService) { }

  ngOnInit() {
    // Nos suscribimos al observable showHeader$ del servicio para actualizar la visibilidad
    this.headerService.showHeader$.subscribe((show) => {
      this.showHeader = show;
    });

    // Detectamos el cambio de rutas para controlar la visibilidad del header
    this.router.events
      .pipe(filter((event) => event instanceof NavigationEnd))
      .subscribe((event) => {
        const currentRoute = this.router.url;

        // Verificamos si la ruta actual está en las rutas donde no queremos mostrar el header
        if (this.routesWithoutHeader.includes(currentRoute)) {
          this.headerService.setShowHeader(false); // Ocultar el header
        } else {
          this.headerService.setShowHeader(true); // Mostrar el header
        }
      });
  }
}
