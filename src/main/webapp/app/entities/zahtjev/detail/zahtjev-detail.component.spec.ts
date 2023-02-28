import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ZahtjevDetailComponent } from './zahtjev-detail.component';

describe('Zahtjev Management Detail Component', () => {
  let comp: ZahtjevDetailComponent;
  let fixture: ComponentFixture<ZahtjevDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ZahtjevDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ zahtjev: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ZahtjevDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ZahtjevDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load zahtjev on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.zahtjev).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
