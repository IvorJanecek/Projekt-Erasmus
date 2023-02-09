import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { FakultetFormService } from './fakultet-form.service';
import { FakultetService } from '../service/fakultet.service';
import { IFakultet } from '../fakultet.model';

import { FakultetUpdateComponent } from './fakultet-update.component';

describe('Fakultet Management Update Component', () => {
  let comp: FakultetUpdateComponent;
  let fixture: ComponentFixture<FakultetUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let fakultetFormService: FakultetFormService;
  let fakultetService: FakultetService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [FakultetUpdateComponent],
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
      .overrideTemplate(FakultetUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FakultetUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    fakultetFormService = TestBed.inject(FakultetFormService);
    fakultetService = TestBed.inject(FakultetService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const fakultet: IFakultet = { id: 456 };

      activatedRoute.data = of({ fakultet });
      comp.ngOnInit();

      expect(comp.fakultet).toEqual(fakultet);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFakultet>>();
      const fakultet = { id: 123 };
      jest.spyOn(fakultetFormService, 'getFakultet').mockReturnValue(fakultet);
      jest.spyOn(fakultetService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fakultet });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: fakultet }));
      saveSubject.complete();

      // THEN
      expect(fakultetFormService.getFakultet).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(fakultetService.update).toHaveBeenCalledWith(expect.objectContaining(fakultet));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFakultet>>();
      const fakultet = { id: 123 };
      jest.spyOn(fakultetFormService, 'getFakultet').mockReturnValue({ id: null });
      jest.spyOn(fakultetService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fakultet: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: fakultet }));
      saveSubject.complete();

      // THEN
      expect(fakultetFormService.getFakultet).toHaveBeenCalled();
      expect(fakultetService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFakultet>>();
      const fakultet = { id: 123 };
      jest.spyOn(fakultetService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fakultet });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(fakultetService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
