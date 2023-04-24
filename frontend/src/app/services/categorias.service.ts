import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable, catchError, throwError, map } from 'rxjs';
import Swal from 'sweetalert2';

@Injectable({
  providedIn: 'root'
})
export class CategoriasService {
  private urlEndPoint: string = 'http://localhost:8081/api/categoria';

  constructor(private http: HttpClient, private router: Router) { }

  getCategoria(): Observable<any> {
    return this.http.get<any>(this.urlEndPoint).pipe(
      catchError(e => {
        this.router.navigate(['/productos']);
        console.error(e.error.mensaje);
        return throwError(() => {
          const error: any = new Error(e.error.mensaje);
          return error;
        });
      }),
      /*map(data => {
        return data.map(producto => {
          return {
            id: producto.id,
            nombre: producto.nombre,
          };
        });
      })*/
    );
  }
}
