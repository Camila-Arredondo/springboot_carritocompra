import { Component, ElementRef, Input, ViewChild } from '@angular/core';
import { AuthService } from '@auth0/auth0-angular';
import { ToastrService } from 'ngx-toastr';
import { Ventas } from 'src/app/services/ventas';
import { VentasService } from 'src/app/services/ventas.service';
import Swal from 'sweetalert2';
import { ActivatedRoute, Router } from '@angular/router';
import { faEye } from '@fortawesome/free-solid-svg-icons';
import { CarritoService } from 'src/app/services/carrito.service';
import jsPDF from 'jspdf';
import html2canvas from 'html2canvas';

@Component({
  selector: 'app-venta',
  templateUrl: './venta.component.html',
  styleUrls: ['./venta.component.css']
})
export class VentaComponent {
  @Input() venta : any = {};
  @ViewChild('ventahtml') ventahtml!: ElementRef;

  user : any = {};
  faEye= faEye;
  carrito: any;


  constructor(private ventaService: VentasService, private auth: AuthService, private toastr: ToastrService,   private router: Router,     private activatedRoute: ActivatedRoute,
    ) {}
  ngOnInit(): void {
    this.auth.user$.subscribe((success: any) => {
      this.user = success;
      this.getVenta(this.user.email);
    })
  }

  totalPrecioCarito() {
    let suma = 0;
    let cantidad = 0;
    if(this.venta?.detalleventa){
      this.venta?.detalleventa.forEach((item: any)=>{
        suma += (item.cantidad * item.producto.precio);
        cantidad += item.cantidad;
      });
    }



    return suma;
  }
  imprimirPDF() {
    const DATA = this.ventahtml.nativeElement;
    const doc = new jsPDF('p', 'pt', 'a4');
    const options = {
      background: 'white',
      scale: 2
    };

    html2canvas(DATA, options).then((canvas) => {
      const img = canvas.toDataURL('image/png');

      const bufferX = 30;
      const bufferY = 30;
      const imgProps = (doc as any).getImageProperties(img);
      const pdfWidth = 535.28; // ancho de A4 menos los márgenes
      const pdfHeight = (imgProps.height * pdfWidth) / imgProps.width;

      doc.addImage(img, 'PNG', bufferX, bufferY, pdfWidth, pdfHeight, undefined, 'FAST');

      if (pdfHeight >= bufferX + bufferY) {
        // ...
      }

      return doc;
    }).then((docResult) => {
      docResult.save(`Venta.pdf`);
    });
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
  getVenta(username: string){
    this.activatedRoute.params.subscribe(params => {
      let id = params['id'];
      if(id){
        this.ventaService.getVenta(id, username).subscribe(res=>{
          this.venta = res;
        });

      }
    });
  }

}
