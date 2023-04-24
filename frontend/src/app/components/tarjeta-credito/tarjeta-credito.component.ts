import { Component } from '@angular/core';
import { AbstractControl, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ValorescompartidosService } from 'src/app/services/valorescompartidos.service';
import { VentasService } from 'src/app/services/ventas.service';
import Swal from 'sweetalert2';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '@auth0/auth0-angular';

@Component({
  selector: 'app-tarjeta-credito',
  templateUrl: './tarjeta-credito.component.html',
  styleUrls: ['./tarjeta-credito.component.css']
})
export class TarjetaCreditoComponent {
  titleCreate : string = "Valida tu tarjeta para realizar el pago";
  user : any = {};
  tarjetaCredito = {
    numerotarjeta : '',
    nombreapellido : '',
    fechavencimiento : '',
    cvv : '',

  };
  submitted : boolean = false;

  form: FormGroup = new FormGroup({

    numerotarjeta : new FormControl(''),
    nombreapellido : new FormControl(''),
    fechavencimiento : new FormControl(''),
    cvv : new FormControl(''),

  })

  constructor(
    private formBuilder: FormBuilder,
    private ventasService: VentasService,
    private auth: AuthService,
    private valorescompartidossvc: ValorescompartidosService,
    private router: Router){}

ngOnInit(): void {
  this.auth.user$.subscribe((success: any) => {
    this.user = success;
  })
  const esValida = this.validarTarjeta("4536721780169195");
  console.log('La tarjeta es válida?', esValida);
  this.form = this.formBuilder.group(
    {
      numerotarjeta : [
        '',
        [
          Validators.required,
          Validators.minLength(16),
          Validators.maxLength(16)
        ]
      ],
      nombreapellido : [
        "",
          [
            Validators.required,
            Validators.minLength(4),
            Validators.maxLength(50)
          ]
      ],
      fechavencimiento : [
        '',
        [
          Validators.required,
          Validators.minLength(4),
          Validators.maxLength(4)
        ]
      ],
      cvv : [
        '',
        [
          Validators.required,
          Validators.min(3),
          Validators.maxLength(3)
        ]
      ],
    }
  )


}
get f():  { [key: string] : AbstractControl } {
  return this.form.controls;
}

validarTarjeta(numerotarjeta: string):boolean{
// validamos si el numero ingresado tiene 16 caracteres
if (numerotarjeta.length === 16) {

  // dividimos el numero de la tarjeta en un arreglo, para separar por caracteres
  let numerotarjetaarreglo = numerotarjeta.split('');

  //recorrer los elementos del arreglo, donde indice indica el inicio,
  //numerotarjetaarreglo.length el numero de vueltas que dará, y el indice++
  //es para indicar como incrementará el valor del indice
  for (let indice = 0; indice < numerotarjetaarreglo.length; indice++) {

    //indices en posicion par
    //% es para obtener el resto para ver si es par o no
    if (indice % 2 === 0) {

      //trabaja en conjunto con el for, por cada vuelta obtengo el ValorDePosicion
      let ValorDePosicion = numerotarjetaarreglo[indice];

      //el indice al ser par se multiplica por dos
      let resultadoMultiplicacionPosicion = parseInt(ValorDePosicion) * 2;

      //si la multi´plicacion es mayor a 9 ....
      if (resultadoMultiplicacionPosicion > 9) {

        //convierto el numero en letras (string)....
        let resultadoindiceString = String(resultadoMultiplicacionPosicion);

        //separo las letras....
        let resultadoindiceArreglo = resultadoindiceString.split('');

        //los paso a numero y se suman....
        let resultado = Number(resultadoindiceArreglo[0]) + Number(resultadoindiceArreglo[1]);

        //esa sumatoria la paso a texto y reemplazo el valor de la posicion por ese resultado.
        numerotarjetaarreglo[indice] = String(resultado);

      } else {

        //de lo contrario (si el resultado de la multiplicacion es menor a 9), se deja el numero y se reemplaza
        numerotarjetaarreglo[indice] = String(resultadoMultiplicacionPosicion);
      }
    }
  }
  let sumaTarjeta = 0;

  for (let indice = 0; indice < numerotarjetaarreglo.length; indice++) {
    let ValorDePosicion = numerotarjetaarreglo[indice];
    sumaTarjeta = sumaTarjeta + Number(ValorDePosicion);
  }

  if (sumaTarjeta % 10 === 0) {
    return true;
  } else {
    return false;
  }


} else {
  return false;
}


}
createVentas():void{
  this.submitted = true;
  if(this.form.invalid){
    return;
  }

  if(!this.validarTarjeta(this.tarjetaCredito.numerotarjeta)){
    Swal.fire({
      icon: 'error',
      title: 'Error Numero de Tarjeta',
      text: 'Tarjeta de credito ingresada Invalida',
    });
    return;
  }
  this.ventasService.createVentas(this.user.email).subscribe(
    ventas => {
      this.valorescompartidossvc.setCantidad(0);
      this.router.navigate(['/venta/'+ventas.nr_venta]);
      Swal.fire({
        icon: 'success',
        title: 'Nueva venta registrada',
        text: 'La venta ha sido registrado satisfactoriamente',
      });
    }
  );
}

}
