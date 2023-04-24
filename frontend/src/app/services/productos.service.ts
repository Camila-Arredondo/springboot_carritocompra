import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable, catchError, throwError, map } from 'rxjs';
import Swal from 'sweetalert2';
import { Productos } from './productos';


@Injectable({
  providedIn: 'root'
})
export class ProductosService {

  private urlEndPoint: string = 'http://localhost:8081/api/productos';

  private httpHeaders = new HttpHeaders({'Content-type': 'application/json'})

  constructor(private http: HttpClient, private router: Router, ) { }

  getProductos(username: string): Observable<any> {
    return this.http.get<any>(this.urlEndPoint, {
      headers: {
        "username": username
      }
    }).pipe(
      catchError(e => {
        this.router.navigate([`/productos`]);
        console.error(e.error.mensaje);
        return throwError(() => {
          const error: any = new Error(e.error.mensaje);
          return error;
        });
      })
    );
  }

  getProducto(id: number, username: string): Observable<any> {
    return this.http.get<any>(`${this.urlEndPoint}/${id}`, {
      headers: {
        "username": username
      }
    }).pipe(
      catchError(e => {
        this.router.navigate([`/productos`]);
        console.error(e.error.mensaje);
        Swal.fire(e.error.mensaje, e.error.error, 'error');
        return throwError(() => {
          const error: any = new Error(e.error.mensaje);
          return error;
        });
      })
    );
  }

  createProducto(producto: Productos) : Observable<any> {
    return this.http.post(this.urlEndPoint, {
      ...producto,
      categoria: {
        id: producto.categoria
      }
    }, { headers : this.httpHeaders}).pipe(
      map((response: any) => response.producto as Productos),
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

  updateProducto(producto : Productos) : Observable<any> {
    return this.http.patch(`${this.urlEndPoint}/${producto.id}`,{
      ...producto,
      categoria: {
        id: producto.categoria
      }
    }, { headers : this.httpHeaders }).pipe(
      catchError(e => {
        console.error(e.error.mensaje);
        Swal.fire(e.error.mensaje, e.error.error, 'error');
        return throwError(() => {
          const error: any = new Error(e.error.mensaje);
          return error;
        });
      })
    );
  }

  deleteProducto(id: number): Observable<any> {
    return this.http.delete<any>(`${this.urlEndPoint}/${id}`,{ headers : this.httpHeaders}).pipe(
      catchError(e => {
        console.error(e.error.mensaje);
        Swal.fire(e.error.mensaje, e.error.error, 'error');
        return throwError(() => {
          const error: any = new Error(e.error.mensaje);
          return error;
        });
      })
    );
  }



}
