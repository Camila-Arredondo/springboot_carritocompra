import { LOCALE_ID,NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './components/header/header.component';
import { FooterComponent } from './components/footer/footer.component';
import { HomeComponent } from './components/home/home.component';
import { ProductosComponent } from './components/productos/productos.component';
import { CarritoComponent } from './components/carrito/carrito.component';
import { MisComprasComponent } from './components/mis-compras/mis-compras.component';
import { NosotrosComponent } from './components/nosotros/nosotros.component';
import { PerfilComponent } from './components/perfil/perfil.component';
import { Page404Component } from './components/page404/page404.component';
import { FormularioComponent } from './components/productos/formulario/formulario.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { OrderListPipe } from './pipes/order-list.pipe';


import { HttpClientModule } from '@angular/common/http';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { NgxMaskDirective, NgxMaskPipe, provideNgxMask } from 'ngx-mask';
import { AuthModule } from '@auth0/auth0-angular';
import { environment } from 'src/environments/environment';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { ToastrModule } from 'ngx-toastr';
import { VentasComponent } from './components/ventas/ventas.component';
import { VentaComponent } from './components/venta/venta.component';
import { TarjetaCreditoComponent } from './components/tarjeta-credito/tarjeta-credito.component';


@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    HomeComponent,
    ProductosComponent,
    CarritoComponent,
    MisComprasComponent,
    NosotrosComponent,
    PerfilComponent,
    Page404Component,
    FormularioComponent,
    OrderListPipe,
    VentasComponent,
    VentaComponent,
    TarjetaCreditoComponent,

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FontAwesomeModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    NgxMaskDirective,
    NgxMaskPipe,
    AuthModule.forRoot(environment.auth0),
    BrowserAnimationsModule, // required animations module
    ToastrModule.forRoot(), // ToastrModule added


  ],
  providers: [provideNgxMask()],
  bootstrap: [AppComponent]
})
export class AppModule { }
