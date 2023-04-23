import { Component, Input, OnInit } from '@angular/core';
import { AuthService } from '@auth0/auth0-angular';
import { faTriangleExclamation, faPenSquare, faTrashCan, faUserPlus, faPenToSquare, faCartPlus, faPlus, faEraser, faMinus } from '@fortawesome/free-solid-svg-icons';
import { ToastrService } from 'ngx-toastr';
import { CarritoService } from 'src/app/services/carrito.service';
import { Productos } from 'src/app/services/productos';
import { ProductosService } from 'src/app/services/productos.service';
import swal from 'sweetalert2';
import jwt_decode from "jwt-decode";

@Component({
  selector: 'app-productos',
  templateUrl: './productos.component.html',
  styleUrls: ['./productos.component.css']
})
export class ProductosComponent implements OnInit{

  @Input() productos : Productos[] = [];
  @Input() mensaje: string = '';
  titulo : string = 'Productos disponibles';
  faExclamation = faTriangleExclamation;
  faEditPerson = faPenSquare;
  faDeletePerson = faTrashCan;
  faAddPerson = faCartPlus;
  faPlus = faPlus;
  faEraser = faEraser;
  faMinus = faMinus;
  user : any = {};

  optionSort: { property: string | null, order : string } = { property : null, order : 'asc' };

  constructor(private productoService: ProductosService, private auth: AuthService, private carritoService: CarritoService, private toastr: ToastrService) {}

ngOnInit(): void {



  this.auth.user$.subscribe((success: any) => {
    console.log(success);
    this.user = success;
    this.getProductos(success.email);
  })


}

getProductos(username: string) : void {
  this.productoService.getProductos(username).subscribe(
    (data) => {
      this.productos = data.producto || [];
      this.mensaje = data.mensaje;
      console.log(this.productos);
      console.log(this.mensaje);
    }
  );
}

orderListProductos(property : string) : void {
  const order = this.optionSort.order;
  this.optionSort = {
    property,
    order : order === 'asc' ? 'desc' : 'asc'
  }
}

eliminarProducto(producto: Productos) : void {
  const swalWithBootstrapButtons = swal.mixin({
    customClass: {
      confirmButton: 'btn btn-success m-2',
      cancelButton: 'btn btn-danger m-2'
    },
    buttonsStyling: false
  })

  swalWithBootstrapButtons.fire({
    title: '¿Estás seguro?',
    text: `Deseas eliminar el producto ${producto.nombre} Esta acción no se puede revertir`,
    icon: 'warning',
    showCancelButton: true,
    confirmButtonText: 'Sí',
    cancelButtonText: 'No',
    reverseButtons: true
  }).then((result) => {
    if (result.isConfirmed) {
      this.productoService.deleteProducto(producto.id).subscribe(
        response => {
          this.productos = this.productos.filter(a => a != producto)
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
accionProducto(producto: Productos, accion: string){
  if(producto.cantidad == 0 && accion == "quitar"){
    return;
  }
  this.carritoService.accionCarrito(producto.id, this.user.email, accion).subscribe(res=>{
    if(accion == "agregar"){
      this.toastr.success("Se agrego " + producto.nombre + " al caritto");
      this.productos = this.productos.map(x=>{
        if(x.id == producto.id){
          x.cantidad++;
        }
        return x;
      });
    }
    if(accion == "quitar"){
      this.toastr.warning("Se quito " + producto.nombre + " del caritto");

      this.productos = this.productos.map(x=>{
        if(x.id == producto.id){

          x.cantidad--;
          if(x.cantidad < 0){
            x.cantidad = 0;
          }
        }
        return x;
      });
    }
  },err=>{
    debugger;
  });
}

}
