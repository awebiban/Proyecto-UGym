import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-lading-page',
  imports: [],
  templateUrl: './lading-page.component.html',
  styleUrl: './lading-page.component.css'
})
export class LadingPageComponent {


  constructor(private router: Router) { }

  login(): void {
    this.router.navigate(['/login']);
  }

  registro(): void {
    this.router.navigate(['/registro']);
  }

}
