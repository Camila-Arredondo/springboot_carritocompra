import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable, catchError, throwError, map, switchMap } from 'rxjs';
import Swal from 'sweetalert2';
import { Carrito } from './carrito';

@Injectable({
  providedIn: 'root'
})
export class CarritoService {
  private urlEndPoint: string = 'http://localhost:8081/api/carrito';
  private httpHeaders = new HttpHeaders({'Content-type': 'application/json'})

  constructor(private http: HttpClient, private router: Router) { }

  getCarrito(username: string): Observable<any> {
    return this.http.get<any>(this.urlEndPoint, {
      headers: {
        "username": username
      }
    }).pipe(
      catchError(e => {
        this.router.navigate([`/carrito`]);
        console.error(e.error.mensaje);
        return throwError(() => {
          const error: any = new Error(e.error.mensaje);
          return error;
        });
      })
    );
  }

  accionCarrito(id: number | null, username: string, accion: string): Observable<any> {
    return this.http.get<any>(`${this.urlEndPoint}/${accion}/${id}`, {
      headers: {
        "username": username
      }
    }).pipe(
      catchError(e => {
        this.router.navigate([`/carrito`]);
        console.error(e.error.mensaje);
        Swal.fire(e.error.mensaje, e.error.error, 'error');
        return throwError(() => {
          const error: any = new Error(e.error.mensaje);
          return error;
        });
      })
    );
  }


  limpiarCarrito(username: string) : Observable<any> {
    return this.http.delete(`${this.urlEndPoint}`,{
      headers: {
        "username": username
      }
    }
    ).pipe(
      map((response: any) => response.producto as Carrito),
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

