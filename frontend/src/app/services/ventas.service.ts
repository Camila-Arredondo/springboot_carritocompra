import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Ventas } from 'src/app/services/ventas';
import { Observable, catchError, throwError, map } from 'rxjs';
import Swal from 'sweetalert2';

@Injectable({
  providedIn: 'root'
})
export class VentasService {
  private urlEndPoint: string = 'http://localhost:8081/api/ventas';


  constructor(private http: HttpClient, private router: Router,) { }


  getVentas(username: string): Observable<any> {
    return this.http.get<any>(this.urlEndPoint, {
      headers: {
        "username": username
      }
    }).pipe(
      catchError(e => {
        this.router.navigate([`/ventas`]);
        console.error(e.error.mensaje);
        return throwError(() => {
          const error: any = new Error(e.error.mensaje);
          return error;
        });
      })
    );
  }

  getVenta(id: number, username: string): Observable<any> {
    return this.http.get<any>(`${this.urlEndPoint}/${id}`, {
      headers: {
        "username": username
      }
    }).pipe(
      catchError(e => {
        this.router.navigate([`/venta`]);
        console.error(e.error.mensaje);
        Swal.fire(e.error.mensaje, e.error.error, 'error');
        return throwError(() => {
          const error: any = new Error(e.error.mensaje);
          return error;
        });
      })
    );
  }

  createVentas(username: string) : Observable<any> {
    return this.http.post(this.urlEndPoint,null, {
      headers: {
        "username":username
      }
    }).pipe(
      map((response: any) => response),
      catchError(e => {
        console.error(e.error.mensaje);
        Swal.fire(e.error.mensaje, e.error.error, 'error');
        return throwError(() => {
          const error: any = new Error(e.error.mensaje);
          return error;
        });
      })
    )
  }
}
