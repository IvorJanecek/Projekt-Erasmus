import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { MobilnostFormService } from './mobilnost-form.service';
import { MobilnostService } from '../service/mobilnost.service';
import { IMobilnost } from '../mobilnost.model';
import { INatjecaj } from 'app/entities/natjecaj/natjecaj.model';
import { NatjecajService } from 'app/entities/natjecaj/service/natjecaj.service';
import { IPrijava } from 'app/entities/prijava/prijava.model';
import { PrijavaService } from 'app/entities/prijava/service/prijava.service';

import { MobilnostUpdateComponent } from './mobilnost-update.component';

describe('Mobilnost Management Update Component', () => {
  let comp: MobilnostUpdateComponent;
  let fixture: ComponentFixture<MobilnostUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let mobilnostFormService: MobilnostFormService;
  let mobilnostService: MobilnostService;
  let natjecajService: NatjecajService;
  let prijavaService: PrijavaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [MobilnostUpdateComponent],
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
      .overrideTemplate(MobilnostUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MobilnostUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    mobilnostFormService = TestBed.inject(MobilnostFormService);
    mobilnostService = TestBed.inject(MobilnostService);
    natjecajService = TestBed.inject(NatjecajService);
    prijavaService = TestBed.inject(PrijavaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call natjecaj query and add missing value', () => {
      const mobilnost: IMobilnost = { id: 456 };
      const natjecaj: INatjecaj = { id: 6659 };
      mobilnost.natjecaj = natjecaj;

      const natjecajCollection: INatjecaj[] = [{ id: 39747 }];
      jest.spyOn(natjecajService, 'query').mockReturnValue(of(new HttpResponse({ body: natjecajCollection })));
      const expectedCollection: INatjecaj[] = [natjecaj, ...natjecajCollection];
      jest.spyOn(natjecajService, 'addNatjecajToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ mobilnost });
      comp.ngOnInit();

      expect(natjecajService.query).toHaveBeenCalled();
      expect(natjecajService.addNatjecajToCollectionIfMissing).toHaveBeenCalledWith(natjecajCollection, natjecaj);
      expect(comp.natjecajsCollection).toEqual(expectedCollection);
    });

    it('Should call prijava query and add missing value', () => {
      const mobilnost: IMobilnost = { id: 456 };
      const prijava: IPrijava = { id: 1470 };
      mobilnost.prijava = prijava;

      const prijavaCollection: IPrijava[] = [{ id: 52424 }];
      jest.spyOn(prijavaService, 'query').mockReturnValue(of(new HttpResponse({ body: prijavaCollection })));
      const expectedCollection: IPrijava[] = [prijava, ...prijavaCollection];
      jest.spyOn(prijavaService, 'addPrijavaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ mobilnost });
      comp.ngOnInit();

      expect(prijavaService.query).toHaveBeenCalled();
      expect(prijavaService.addPrijavaToCollectionIfMissing).toHaveBeenCalledWith(prijavaCollection, prijava);
      expect(comp.prijavasCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const mobilnost: IMobilnost = { id: 456 };
      const natjecaj: INatjecaj = { id: 77144 };
      mobilnost.natjecaj = natjecaj;
      const prijava: IPrijava = { id: 91579 };
      mobilnost.prijava = prijava;

      activatedRoute.data = of({ mobilnost });
      comp.ngOnInit();

      expect(comp.natjecajsCollection).toContain(natjecaj);
      expect(comp.prijavasCollection).toContain(prijava);
      expect(comp.mobilnost).toEqual(mobilnost);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMobilnost>>();
      const mobilnost = { id: 123 };
      jest.spyOn(mobilnostFormService, 'getMobilnost').mockReturnValue(mobilnost);
      jest.spyOn(mobilnostService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ mobilnost });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: mobilnost }));
      saveSubject.complete();

      // THEN
      expect(mobilnostFormService.getMobilnost).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(mobilnostService.update).toHaveBeenCalledWith(expect.objectContaining(mobilnost));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMobilnost>>();
      const mobilnost = { id: 123 };
      jest.spyOn(mobilnostFormService, 'getMobilnost').mockReturnValue({ id: null });
      jest.spyOn(mobilnostService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ mobilnost: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: mobilnost }));
      saveSubject.complete();

      // THEN
      expect(mobilnostFormService.getMobilnost).toHaveBeenCalled();
      expect(mobilnostService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMobilnost>>();
      const mobilnost = { id: 123 };
      jest.spyOn(mobilnostService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ mobilnost });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(mobilnostService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareNatjecaj', () => {
      it('Should forward to natjecajService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(natjecajService, 'compareNatjecaj');
        comp.compareNatjecaj(entity, entity2);
        expect(natjecajService.compareNatjecaj).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('comparePrijava', () => {
      it('Should forward to prijavaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(prijavaService, 'comparePrijava');
        comp.comparePrijava(entity, entity2);
        expect(prijavaService.comparePrijava).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
