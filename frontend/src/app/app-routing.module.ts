import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { NosotrosComponent } from './components/nosotros/nosotros.component';
import { Page404Component } from './components/page404/page404.component';
import { ProductosComponent } from './components/productos/productos.component';
import { HomeComponent } from './components/home/home.component';
import { CarritoComponent } from './components/carrito/carrito.component';
import { FormularioComponent } from './components/productos/formulario/formulario.component';
import { PerfilComponent } from './components/perfil/perfil.component';

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
    path: 'productos', component: ProductosComponent, 
  },
  {
    path: 'productos/formulario', component: FormularioComponent
  },
  {
    path: 'productos/formulario/:id', component: FormularioComponent
  },
  {
    path: 'nosotros', component: NosotrosComponent,
  },
  {
    path: 'carrito', component: CarritoComponent,
  },
  {
    path: 'perfil', component: PerfilComponent,
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
