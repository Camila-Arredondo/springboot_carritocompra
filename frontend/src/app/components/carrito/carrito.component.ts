import { Component, Input, OnInit } from '@angular/core';
import { AuthService } from '@auth0/auth0-angular';
import { Carrito } from 'src/app/services/carrito';
import { CarritoService } from 'src/app/services/carrito.service';
import { ToastrService } from 'ngx-toastr';
import Swal from 'sweetalert2';
import { ActivatedRoute, Router } from '@angular/router';
import swal from 'sweetalert2';
import { faEraser, faMinus, faPlus, faTrash } from '@fortawesome/free-solid-svg-icons';
import { ValorescompartidosService } from 'src/app/services/valorescompartidos.service';
import { VentasService } from 'src/app/services/ventas.service';

@Component({
  selector: 'app-carrito',
  templateUrl: './carrito.component.html',
  styleUrls: ['./carrito.component.css']
})
export class CarritoComponent implements OnInit {
  @Input() carrito : Carrito[] = [];
  @Input() mensaje: string = '';
  titulo : string = 'Productos Agregados';
  finalizarcompra : boolean = false;
  faPlus = faPlus;
  faEraser = faEraser;
  faMinus = faMinus;
  faTrash = faTrash;
  user : any = {};

  optionSort: { property: string | null, order : string } = { property : null, order : 'asc' };

  constructor(
    private carritoService: CarritoService,
    private auth: AuthService,
    private toastr: ToastrService,
    private valorescompartidossvc: ValorescompartidosService,
    private ventasService: VentasService,
    private router: Router
    ) {}

  ngOnInit(): void {
    this.auth.user$.subscribe((success: any) => {
      this.user = success;
      this.getCarrito(success.email);
    })
  }


  getCarrito(username: string) : void {
    this.carritoService.getCarrito(username).subscribe(
      (data) => {
        this.carrito = data.carrito || [];
        this.mensaje = data.mensaje;
        console.log(this.carrito);
        console.log(this.mensaje);
      }
    );
  }
  LimpiarCarrito(){
    if(this.carrito.length == 0) return;
    const swalWithBootstrapButtons = swal.mixin({
      customClass: {
        confirmButton: 'btn btn-success m-2',
        cancelButton: 'btn btn-danger m-2'
      },
      buttonsStyling: false
    })
    swalWithBootstrapButtons.fire({
      title: '¿Estás seguro?',
      text: `¿Desea limpiar el carrito y perder su lista de compras? Esta acción no se puede revertir`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Sí',
      cancelButtonText: 'No',
      reverseButtons: true
    }).then((result) => {
      if (result.isConfirmed) {
        this.carritoService.limpiarCarrito(this.user.email).subscribe(res=>{
          this.valorescompartidossvc.setCantidad(0);
          this.carrito = [];
        });

      }});



  }
  totalPrecioCarito() {
    let suma = 0;
    let cantidad = 0;
    this.carrito.forEach(item=>{
      suma += (item.cantidad * item.producto.precio);
      cantidad += item.cantidad;
    });
    this.valorescompartidossvc.setCantidad(cantidad);


    return suma;
  }

  convertToMoney(numero: string | number){
    let numeroStr = numero.toString();

    let partes = numeroStr.split(".");

    let parteEntera = partes[0].replace(/\B(?=(\d{3})+(?!\d))/g, ",");

    let parteDecimal = "";
    if (partes.length > 1) {
      parteDecimal = "." + partes[1];
    }

    return "$ "+(parteEntera + parteDecimal).replaceAll(',',".");

  }

  orderListCarrito(property : string) : void {
    const order = this.optionSort.order;
    this.optionSort = {
      property,
      order : order === 'asc' ? 'desc' : 'asc'
    }
  }

  eliminarCarrito(carrito: Carrito) : void {
    const swalWithBootstrapButtons = swal.mixin({
      customClass: {
        confirmButton: 'btn btn-success m-2',
        cancelButton: 'btn btn-danger m-2'
      },
      buttonsStyling: false
    })

    swalWithBootstrapButtons.fire({
      title: '¿Estás seguro?',
      text: `Deseas eliminar el producto ${carrito.producto.nombre} Esta acción no se puede revertir`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Sí',
      cancelButtonText: 'No',
      reverseButtons: true
    }).then((result) => {
      if (result.isConfirmed) {
        this.carritoService.accionCarrito (carrito.producto.id,this.user.email,"eliminar" ).subscribe(
          response => {
            this.carrito = this.carrito.filter(a => a != carrito)
            swalWithBootstrapButtons.fire(
              'Producto eliminado',
              'El producto ha sido eliminado',
              'success'
            )
          }
        )
      }
      else if (result.dismiss === swal.DismissReason.cancel)
      {
        swalWithBootstrapButtons.fire(
          'Acción cancelada',
          'Ha cancelado la eliminación del producto',
          'error'
        )
      }
    })
  }
  accionCarrito(carrito: Carrito, accion: string){
    if(carrito.cantidad == 0 && accion == "quitar"){
      return;
    }
    this.carritoService.accionCarrito(carrito.producto.id, this.user.email, accion).subscribe(res=>{
      if(accion == "agregar"){
        this.toastr.success("Se agrego " + carrito.producto.nombre + " al caritto");
        this.carrito = this.carrito.map(x=>{
          if(x.id == carrito.id){
            x.cantidad++;
          }
          return x;
        });
      }
      if(accion == "quitar"){
        this.toastr.warning("Se quito " + carrito.producto.nombre + " del caritto");

        this.carrito = this.carrito.map(x=>{
          if(x.id == carrito.id){

            x.cantidad--;
            if(x.cantidad < 0){
              x.cantidad = 0;
            }
          }
          return x;
        });

        this.carrito = this.carrito.filter(x=>x.cantidad > 0);
      }
    },err=>{
    });
  }

  createVentas():void{
    if(this.carrito.length == 0) return;
    this.finalizarcompra = !this.finalizarcompra;

  }




}
