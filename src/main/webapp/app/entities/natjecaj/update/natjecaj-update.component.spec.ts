import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { NatjecajFormService } from './natjecaj-form.service';
import { NatjecajService } from '../service/natjecaj.service';
import { INatjecaj } from '../natjecaj.model';

import { NatjecajUpdateComponent } from './natjecaj-update.component';

describe('Natjecaj Management Update Component', () => {
  let comp: NatjecajUpdateComponent;
  let fixture: ComponentFixture<NatjecajUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let natjecajFormService: NatjecajFormService;
  let natjecajService: NatjecajService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [NatjecajUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(NatjecajUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(NatjecajUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    natjecajFormService = TestBed.inject(NatjecajFormService);
    natjecajService = TestBed.inject(NatjecajService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const natjecaj: INatjecaj = { id: 456 };

      activatedRoute.data = of({ natjecaj });
      comp.ngOnInit();

      expect(comp.natjecaj).toEqual(natjecaj);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<INatjecaj>>();
      const natjecaj = { id: 123 };
      jest.spyOn(natjecajFormService, 'getNatjecaj').mockReturnValue(natjecaj);
      jest.spyOn(natjecajService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ natjecaj });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: natjecaj }));
      saveSubject.complete();

      // THEN
      expect(natjecajFormService.getNatjecaj).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(natjecajService.update).toHaveBeenCalledWith(expect.objectContaining(natjecaj));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<INatjecaj>>();
      const natjecaj = { id: 123 };
      jest.spyOn(natjecajFormService, 'getNatjecaj').mockReturnValue({ id: null });
      jest.spyOn(natjecajService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ natjecaj: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: natjecaj }));
      saveSubject.complete();

      // THEN
      expect(natjecajFormService.getNatjecaj).toHaveBeenCalled();
      expect(natjecajService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<INatjecaj>>();
      const natjecaj = { id: 123 };
      jest.spyOn(natjecajService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ natjecaj });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(natjecajService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
