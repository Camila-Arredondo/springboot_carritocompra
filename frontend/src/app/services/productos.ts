import { Categoria } from "./categorias";

export class Productos {
    id : number = 0;
    nombre : string = '';
    descripcion : string = '';
    categoria : Categoria = {
      id: 0,
      nombre: ''
    };
    precio : number | null = null;
    cantidad : number = 0;
    foto: string = '';
}
