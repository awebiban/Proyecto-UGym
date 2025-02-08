import { Routes } from '@angular/router';
import { MensajesComponent } from './component/mensajes/mensajes.component';
import { PerfilUsuarioComponent } from './component/perfil-usuario/perfil-usuario.component';
import { ReservasComponent } from './component/reservas/reservas.component';
import { LadingPageComponent } from './component/lading-page/lading-page.component'
import { LoginComponent } from './component/login/login.component';
import { BonosComponent } from './component/bonos/bonos.component';
import { ListaUsuariosComponent } from './component/lista-usuarios/lista-usuarios.component';
import { RegistroComponent } from './component/registro/registro.component';
import { AltaEmpleadoComponent } from './component/alta-empleado/alta-empleado.component';
import { ReservaPrivadaComponent } from './component/reserva-privada/reserva-privada.component';
import { EditarUsuarioComponent } from './component/editar-usuario/editar-usuario.component';
import { CajaComponent } from './component/caja/caja.component';
import { ListaHorarioComponent } from './component/lista-horario/lista-horario.component';
import { ModificarClaseComponent } from './component/modificar-clase/modificar-clase.component';
import { HorarioComponent } from './component/horario/horario.component';


export const routes: Routes = [
    { path: '', component: LadingPageComponent },
    { path: 'listadoUsuario', component: ListaUsuariosComponent },
    { path: 'altaEmpleado', component: AltaEmpleadoComponent },
    { path: 'login', component: LoginComponent },
    { path: 'registro', component: RegistroComponent },
    { path: 'lista-horario', component: ListaHorarioComponent },
    { path: 'modificar-clase/:id', component: ModificarClaseComponent },
    { path: 'editarUsuario/:id', component: EditarUsuarioComponent },
    {
        path: '', component: CajaComponent,
        children: [
            { path: 'perfil-usuario', component: PerfilUsuarioComponent },
            { path: 'horario', component: HorarioComponent },
            { path: 'mensajes', component: MensajesComponent },
            { path: 'reservas', component: ReservasComponent },
            { path: 'bonos', component: BonosComponent },
            { path: 'bonos/confirm-payment', component: BonosComponent },
            { path: 'reserva-privada', component: ReservaPrivadaComponent },
        ]
    }
];