import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FakultetDetailComponent } from './fakultet-detail.component';

describe('Fakultet Management Detail Component', () => {
  let comp: FakultetDetailComponent;
  let fixture: ComponentFixture<FakultetDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [FakultetDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ fakultet: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(FakultetDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(FakultetDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load fakultet on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.fakultet).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
