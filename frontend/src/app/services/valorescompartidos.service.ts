import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ValorescompartidosService {
  private datosCompartidos: any = {};

  constructor() { }

  setDatosCompartidos(datos: any): void {
    this.datosCompartidos = datos;
  }

  getDatosCompartidos(): any {
    return this.datosCompartidos;
  }

  setCantidad(valor: any){
    this.datosCompartidos.cantidadProductos = valor;
  }
}
