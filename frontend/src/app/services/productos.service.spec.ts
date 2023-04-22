import { TestBed } from '@angular/core/testing';

import { ProductosService } from './productos.service';

describe('ProductosService', () => {
  let producto: ProductosService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    producto = TestBed.inject(ProductosService);
  });

  it('should be created', () => {
    expect(producto).toBeTruthy();
  });
});
