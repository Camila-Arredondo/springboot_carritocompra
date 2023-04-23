export interface Carrito {
  id: number;
  cantidad: number;
  productoid?: any;
  usuario: string;
  producto: Producto;
}

export interface Producto {
  id: number;
  nombre: string;
  descripcion: string;
  foto: string;
  precio: number;
  cantidad: number;
  categoria: Categoria;
}

export interface Categoria {
  id: number;
  nombre: string;
}
