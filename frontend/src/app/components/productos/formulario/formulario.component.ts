import { Component, OnInit} from '@angular/core';
import { Productos } from 'src/app/services/productos';
import { AbstractControl, FormGroup, FormControl, FormBuilder, Validators } from '@angular/forms';
import { ProductosService } from 'src/app/services/productos.service';
import { ActivatedRoute, Router } from '@angular/router';

import Swal from 'sweetalert2';
import { HttpClient } from '@angular/common/http';
import { CategoriasService } from 'src/app/services/categorias.service';
import { Categoria } from 'src/app/services/categorias';
import { DomSanitizer } from '@angular/platform-browser';
import { AuthService } from '@auth0/auth0-angular';

@Component({
  selector: 'app-formulario',
  templateUrl: './formulario.component.html',
  styleUrls: ['./formulario.component.css']
})
export class FormularioComponent implements OnInit{

  titleCreate : string = "Registra un nuevo producto";
  titleUpdate : string = "Actualiza los datos del producto";
  producto: Productos = new Productos();
  categoria: Categoria[] =  [];
  imagenPreview = "";
  fileSelected?: any;
  submitted : boolean = false;
  user : any = {};

  form: FormGroup = new FormGroup({

    nombre : new FormControl(''),
    categoria : new FormControl(''),
    descripcion : new FormControl(''),
    precio : new FormControl(''),
    foto : new FormControl('')

  })

  constructor(
    private formBuilder: FormBuilder,
    private productoService: ProductosService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private categoriaService: CategoriasService,
    private sant: DomSanitizer,
    private auth: AuthService
    ) { }

ngOnInit(): void {
  this.auth.user$.subscribe((success: any) => {
    this.user = success;
    this.getProducto(success.email);
    this.getCategoria();
  });
  this.form = this.formBuilder.group(
    {
      foto : [
        '',
        [
          Validators.required,

        ]
      ],
      nombre : [
        '',
        [
          Validators.required,
          Validators.minLength(2),
          Validators.maxLength(50)
        ]
      ],
      categoria : [
        "",
          [
            Validators.required,
          ]
      ],
      descripcion : [
        '',
        [
          Validators.required,
          Validators.minLength(2),
          Validators.maxLength(200)
        ]
      ],
      precio : [
        '',
        [
          Validators.required,
          Validators.min(1)
        ]
      ],
    }
  )
}

get f():  { [key: string] : AbstractControl } {
  return this.form.controls;
}

seleccionarImagen(imagen: any): void{
  this.fileSelected = imagen.target?.files[0];
  this.imagenPreview = this.sant.bypassSecurityTrustUrl(window.URL.createObjectURL(this.fileSelected)) as string;

  this.convertFileToBase64();
}

convertFileToBase64(): void {
  let reader = new FileReader();
  reader.readAsDataURL(this.fileSelected as Blob);
  reader.onloadend = () =>{
    this.producto.foto = reader.result as string;
  }
}

onSubmit() : void {
  this.submitted = true;
  if(this.form.invalid){
    return;
  }
  this.createProducto();
}

onReset(): void {
  this.submitted = false;
  this.form.reset();
}

createProducto() : void {
  this.productoService.createProducto(this.producto).subscribe(
    producto => {
      console.log(producto);
      this.router.navigate(['/productos']);
      Swal.fire({
        icon: 'success',
        title: 'Nuevo producto registrado',
        text: 'El producto ' + producto.nombre + ' ' + ' ha sido registrado satisfactoriamente',
      });
    }
  );
}

getProducto(username: string): void {
  this.activatedRoute.params.subscribe(params => {
    let id = params['id'];
    if(id){
      this.productoService.getProducto(id, username).subscribe(
        (producto) => {
          this.producto = {
            ...producto,
            categoria: producto.categoria.id
          };
        }
      )
    }
  });
}

getCategoria(): void {
  this.categoriaService.getCategoria().subscribe((categoria)=>{
    this.categoria = categoria.categoria;
    console.log(categoria);
  });
}

updateProducto(): void {
  this.productoService.updateProducto(this.producto).subscribe(
    producto => {
      this.router.navigateByUrl('/productos');
      Swal.fire({
        position: 'center',
        icon: 'success',
        title: 'Datos actualizados',
        text: `Los datos del producto ${producto.producto.nombre}  se han actualizado correctamente`,
        timer: 2000,
        showConfirmButton: false,
      });
    }
  );
}

}
