import { Component, Input, OnInit } from '@angular/core';
import { AuthService } from '@auth0/auth0-angular';
import { Carrito } from 'src/app/services/carrito';
import { CarritoService } from 'src/app/services/carrito.service';
import swal from 'sweetalert2';

@Component({
  selector: 'app-carrito',
  templateUrl: './carrito.component.html',
  styleUrls: ['./carrito.component.css']
})
export class CarritoComponent implements OnInit {
  @Input() carrito : Carrito[] = [];
  @Input() mensaje: string = '';

  user : any = {};

  optionSort: { property: string | null, order : string } = { property : null, order : 'asc' };

  constructor(private carritoService: CarritoService, private auth: AuthService) {}

  ngOnInit(): void {
    this.auth.user$.subscribe((success: any) => {
      this.user = success;
      this.getCarrito(success.email);
    })
  }


  getCarrito(username: string) : void {
    this.carritoService.getCarrito(username).subscribe(
      (data) => {
        this.carrito = data.producto || [];
        this.mensaje = data.mensaje;
        console.log(this.carrito);
        console.log(this.mensaje);
      }
    );
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
      text: `Deseas eliminar el producto ${carrito.productoid} Esta acción no se puede revertir`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Sí',
      cancelButtonText: 'No',
      reverseButtons: true
    }).then((result) => {
      if (result.isConfirmed) {
        this.carritoService.accionCarrito (carrito.productoid,this.user.email,"eliminar" ).subscribe(
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



}
