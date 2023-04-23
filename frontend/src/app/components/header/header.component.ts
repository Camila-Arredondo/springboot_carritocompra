import { Component, OnInit, Inject } from '@angular/core';
import { DOCUMENT, Location } from '@angular/common';
import { AuthService } from '@auth0/auth0-angular';
import { Router } from '@angular/router';
import { interval } from 'rxjs';
import { ValorescompartidosService } from 'src/app/services/valorescompartidos.service';
import { faShoppingCart } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit{
myDate = Date.now();
isAuthenticated: boolean = false;
  currentRoute: string = '';
  route: string = '';
  cantidadCarrito = 0;
  faShoppingCart = faShoppingCart;

  horaActual = new Date();
  constructor(
    @Inject(DOCUMENT) public document: Document,
    public auth: AuthService,
    private location: Location,
    private router: Router,
    private valorescompartidossvc: ValorescompartidosService
  ) {

    const timer = interval(1000);

    timer.subscribe(() => {
      this.cantidadCarrito = this.valorescompartidossvc.getDatosCompartidos()?.cantidadProductos || 0;
      this.horaActual = new Date();
    });
  }

  ngOnInit(): void {
    this.auth.isAuthenticated$.subscribe((success: boolean) => {
      this.isAuthenticated = success;
    })
  }

  login(): void {
    this.route = this.location.path();
    this.auth.loginWithRedirect({
      appState: { target : this.route}
    });
  }


  logout(): void {
    this.auth.logout();
  }

}
