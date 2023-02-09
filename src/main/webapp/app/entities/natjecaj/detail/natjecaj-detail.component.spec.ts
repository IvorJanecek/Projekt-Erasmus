import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NatjecajDetailComponent } from './natjecaj-detail.component';

describe('Natjecaj Management Detail Component', () => {
  let comp: NatjecajDetailComponent;
  let fixture: ComponentFixture<NatjecajDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [NatjecajDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ natjecaj: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(NatjecajDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(NatjecajDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load natjecaj on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.natjecaj).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
