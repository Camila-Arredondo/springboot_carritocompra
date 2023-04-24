import { Component, Input } from '@angular/core';
import { AuthService } from '@auth0/auth0-angular';
import { ToastrService } from 'ngx-toastr';
import { Ventas } from 'src/app/services/ventas';
import { VentasService } from 'src/app/services/ventas.service';
import Swal from 'sweetalert2';
import { ActivatedRoute, Router } from '@angular/router';
import { faEye } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-ventas',
  templateUrl: './ventas.component.html',
  styleUrls: ['./ventas.component.css']
})
export class VentasComponent {
  @Input() ventas : Ventas[] = [];
  user : any = {};
  faEye= faEye;


  constructor(private ventasService: VentasService, private auth: AuthService, private toastr: ToastrService,   private router: Router) {}
  ngOnInit(): void {
    this.auth.user$.subscribe((success: any) => {
      this.user = success;
      this.getVenta(success.email);
    })
  }


  getVenta(username: string) : void {
    this.ventasService.getVentas(username).subscribe(
      (data) => {
        this.ventas = data.ventas || [];
        console.log(this.ventas);
      }
    );
  }


}
