import { Component, Input, OnInit } from '@angular/core';
import { faTriangleExclamation, faPenSquare, faTrashCan, faUserPlus, faPenToSquare, faCartPlus, faPlus, faEraser, faMinus } from '@fortawesome/free-solid-svg-icons';
import { Productos } from 'src/app/services/productos';
import { ProductosService } from 'src/app/services/productos.service';
import swal from 'sweetalert2';

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

  optionSort: { property: string | null, order : string } = { property : null, order : 'asc' };

  constructor(private productoService: ProductosService) {}

ngOnInit(): void {
  this.getProductos();
}

getProductos() : void {
  this.productoService.getProductos().subscribe(
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

}
