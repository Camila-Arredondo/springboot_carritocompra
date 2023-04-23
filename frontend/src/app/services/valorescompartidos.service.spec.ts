import { TestBed } from '@angular/core/testing';

import { ValorescompartidosService } from './valorescompartidos.service';

describe('ValorescompartidosService', () => {
  let service: ValorescompartidosService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ValorescompartidosService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
