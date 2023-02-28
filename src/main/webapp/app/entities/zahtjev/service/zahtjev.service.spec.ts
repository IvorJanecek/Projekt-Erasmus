import { TestBed } from '@angular/core/testing';

import { ZahtjevService } from './zahtjev.service';

describe('ZahtjevService', () => {
  let service: ZahtjevService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ZahtjevService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
