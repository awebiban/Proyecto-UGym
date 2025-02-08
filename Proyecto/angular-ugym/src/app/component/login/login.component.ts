import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { LoginService } from '../../services/login.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  email = '';
  password = '';
  error = '';

  constructor(private loginService: LoginService, private router: Router) { }

  atras(): void {
    this.router.navigate(['/']);
  }

  login(): void {

    if (!this.validarEmail(this.email)) {
      this.error = 'Formato de email incorrecto';
      return;
    }

    this.loginService.login(this.email, this.password).subscribe({
      next: (response: any) => {
        console.log('Login exitoso:', response);

        this.loginService.setAuthenticated(response.id_usuario);

        sessionStorage.setItem('id_usuario', response);

        this.router.navigate(['/horario']).then(() => {
          window.location.reload();
        })
      },
      error: (error) => {
        this.error = 'Credenciales inválidas. Intente nuevamente.';
        console.error('Error de login:', error);
      }
    });
  }

  register(): void {
    this.router.navigate(['/registro']);
  }

  validarEmail(email: string): boolean {
    const re = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    return re.test(email);
  }

}
