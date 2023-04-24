import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { NosotrosComponent } from './components/nosotros/nosotros.component';
import { Page404Component } from './components/page404/page404.component';
import { ProductosComponent } from './components/productos/productos.component';
import { HomeComponent } from './components/home/home.component';
import { CarritoComponent } from './components/carrito/carrito.component';
import { PerfilComponent } from './components/perfil/perfil.component';
import { AuthGuard } from '@auth0/auth0-angular';
import { FormularioComponent } from './components/productos/formulario/formulario.component';
import { VentaComponent } from './components/venta/venta.component';
import { TarjetaCreditoComponent } from './components/tarjeta-credito/tarjeta-credito.component';



const routes: Routes = [
  {
    path: '', component: HomeComponent,
  },
  {
    path: 'home', redirectTo: '',
  },
  {
    path: 'index', redirectTo: '',
  },
  {
    path: 'productos', component: ProductosComponent, canActivate: [AuthGuard]
  },
  {
    path: 'productos/formulario', component: FormularioComponent,canActivate: [AuthGuard]
  },
  {
    path: 'productos/formulario/:id', component: FormularioComponent,canActivate: [AuthGuard]
  },
  {
    path: 'nosotros', component: NosotrosComponent,
  },
  {
    path: 'validaciontarjeta', component: TarjetaCreditoComponent,canActivate: [AuthGuard]
  },
  {
    path: 'carrito', component: CarritoComponent,canActivate: [AuthGuard]
  },
  {
    path: 'perfil', component: PerfilComponent,canActivate: [AuthGuard]
  },
  {
    path: 'venta/:id', component: VentaComponent,canActivate: [AuthGuard]
  },
  {
    path: '404', component: Page404Component,
  },
  {
    path: '**', redirectTo: '404',
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
