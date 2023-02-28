import { TestBed } from '@angular/core/testing';

import { ZahtjevFormService } from './zahtjev-form.service';

describe('ZahtjevFormService', () => {
  let service: ZahtjevFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ZahtjevFormService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
