import { Component } from '@angular/core';
import { RouterModule, Routes} from '@angular/router';
import { SidebarComponent } from "../sidebar/sidebar.component";

@Component({
  selector: 'caja',
  imports: [RouterModule, SidebarComponent],
  templateUrl: './caja.component.html',
  styleUrl: './caja.component.css'
})
export class CajaComponent {

}
