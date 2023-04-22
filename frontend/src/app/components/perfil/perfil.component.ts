import { Component, OnInit } from '@angular/core';
import { AuthService} from '@auth0/auth0-angular';
@Component({
  selector: 'app-perfil',
  templateUrl: './perfil.component.html',
  styleUrls: ['./perfil.component.css']
})
export class PerfilComponent implements OnInit{
  user : any = {};

  constructor(private auth: AuthService){}

  ngOnInit(): void {
    this.getPerfil();
  }

  getPerfil(): void {
    this.auth.user$.subscribe((success: any) => {
      this.user = success;
      console.log(this.user);
    })
  }

}
