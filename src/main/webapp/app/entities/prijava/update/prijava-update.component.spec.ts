import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PrijavaFormService } from './prijava-form.service';
import { PrijavaService } from '../service/prijava.service';
import { IPrijava } from '../prijava.model';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { IFakultet } from 'app/entities/fakultet/fakultet.model';
import { FakultetService } from 'app/entities/fakultet/service/fakultet.service';
import { INatjecaj } from 'app/entities/natjecaj/natjecaj.model';
import { NatjecajService } from 'app/entities/natjecaj/service/natjecaj.service';

import { PrijavaUpdateComponent } from './prijava-update.component';

describe('Prijava Management Update Component', () => {
  let comp: PrijavaUpdateComponent;
  let fixture: ComponentFixture<PrijavaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let prijavaFormService: PrijavaFormService;
  let prijavaService: PrijavaService;
  let userService: UserService;
  let fakultetService: FakultetService;
  let natjecajService: NatjecajService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PrijavaUpdateComponent],
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
      .overrideTemplate(PrijavaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PrijavaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    prijavaFormService = TestBed.inject(PrijavaFormService);
    prijavaService = TestBed.inject(PrijavaService);
    userService = TestBed.inject(UserService);
    fakultetService = TestBed.inject(FakultetService);
    natjecajService = TestBed.inject(NatjecajService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call User query and add missing value', () => {
      const prijava: IPrijava = { id: 456 };
      const user: IUser = { id: 13911 };
      prijava.user = user;

      const userCollection: IUser[] = [{ id: 24130 }];
      jest.spyOn(userService, 'query').mockReturnValue(of(new HttpResponse({ body: userCollection })));
      const additionalUsers = [user];
      const expectedCollection: IUser[] = [...additionalUsers, ...userCollection];
      jest.spyOn(userService, 'addUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ prijava });
      comp.ngOnInit();

      expect(userService.query).toHaveBeenCalled();
      expect(userService.addUserToCollectionIfMissing).toHaveBeenCalledWith(
        userCollection,
        ...additionalUsers.map(expect.objectContaining)
      );
      expect(comp.usersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Fakultet query and add missing value', () => {
      const prijava: IPrijava = { id: 456 };
      const fakultet: IFakultet = { id: 23877 };
      prijava.fakultet = fakultet;

      const fakultetCollection: IFakultet[] = [{ id: 93412 }];
      jest.spyOn(fakultetService, 'query').mockReturnValue(of(new HttpResponse({ body: fakultetCollection })));
      const additionalFakultets = [fakultet];
      const expectedCollection: IFakultet[] = [...additionalFakultets, ...fakultetCollection];
      jest.spyOn(fakultetService, 'addFakultetToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ prijava });
      comp.ngOnInit();

      expect(fakultetService.query).toHaveBeenCalled();
      expect(fakultetService.addFakultetToCollectionIfMissing).toHaveBeenCalledWith(
        fakultetCollection,
        ...additionalFakultets.map(expect.objectContaining)
      );
      expect(comp.fakultetsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Natjecaj query and add missing value', () => {
      const prijava: IPrijava = { id: 456 };
      const natjecaj: INatjecaj = { id: 26279 };
      prijava.natjecaj = natjecaj;

      const natjecajCollection: INatjecaj[] = [{ id: 16526 }];
      jest.spyOn(natjecajService, 'query').mockReturnValue(of(new HttpResponse({ body: natjecajCollection })));
      const additionalNatjecajs = [natjecaj];
      const expectedCollection: INatjecaj[] = [...additionalNatjecajs, ...natjecajCollection];
      jest.spyOn(natjecajService, 'addNatjecajToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ prijava });
      comp.ngOnInit();

      expect(natjecajService.query).toHaveBeenCalled();
      expect(natjecajService.addNatjecajToCollectionIfMissing).toHaveBeenCalledWith(
        natjecajCollection,
        ...additionalNatjecajs.map(expect.objectContaining)
      );
      expect(comp.natjecajsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const prijava: IPrijava = { id: 456 };
      const user: IUser = { id: 19606 };
      prijava.user = user;
      const fakultet: IFakultet = { id: 9662 };
      prijava.fakultet = fakultet;
      const natjecaj: INatjecaj = { id: 54554 };
      prijava.natjecaj = natjecaj;

      activatedRoute.data = of({ prijava });
      comp.ngOnInit();

      expect(comp.usersSharedCollection).toContain(user);
      expect(comp.fakultetsSharedCollection).toContain(fakultet);
      expect(comp.natjecajsSharedCollection).toContain(natjecaj);
      expect(comp.prijava).toEqual(prijava);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPrijava>>();
      const prijava = { id: 123 };
      jest.spyOn(prijavaFormService, 'getPrijava').mockReturnValue(prijava);
      jest.spyOn(prijavaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ prijava });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: prijava }));
      saveSubject.complete();

      // THEN
      expect(prijavaFormService.getPrijava).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(prijavaService.update).toHaveBeenCalledWith(expect.objectContaining(prijava));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPrijava>>();
      const prijava = { id: 123 };
      jest.spyOn(prijavaFormService, 'getPrijava').mockReturnValue({ id: null });
      jest.spyOn(prijavaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ prijava: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: prijava }));
      saveSubject.complete();

      // THEN
      expect(prijavaFormService.getPrijava).toHaveBeenCalled();
      expect(prijavaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPrijava>>();
      const prijava = { id: 123 };
      jest.spyOn(prijavaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ prijava });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(prijavaService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareUser', () => {
      it('Should forward to userService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(userService, 'compareUser');
        comp.compareUser(entity, entity2);
        expect(userService.compareUser).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareFakultet', () => {
      it('Should forward to fakultetService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(fakultetService, 'compareFakultet');
        comp.compareFakultet(entity, entity2);
        expect(fakultetService.compareFakultet).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareNatjecaj', () => {
      it('Should forward to natjecajService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(natjecajService, 'compareNatjecaj');
        comp.compareNatjecaj(entity, entity2);
        expect(natjecajService.compareNatjecaj).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
