import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../natjecaj.test-samples';

import { NatjecajFormService } from './natjecaj-form.service';

describe('Natjecaj Form Service', () => {
  let service: NatjecajFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(NatjecajFormService);
  });

  describe('Service methods', () => {
    describe('createNatjecajFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createNatjecajFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            description: expect.any(Object),
            createDate: expect.any(Object),
            datumOd: expect.any(Object),
            datumDo: expect.any(Object),
            status: expect.any(Object),
          })
        );
      });

      it('passing INatjecaj should create a new form with FormGroup', () => {
        const formGroup = service.createNatjecajFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            description: expect.any(Object),
            createDate: expect.any(Object),
            datumOd: expect.any(Object),
            datumDo: expect.any(Object),
            status: expect.any(Object),
          })
        );
      });
    });

    describe('getNatjecaj', () => {
      it('should return NewNatjecaj for default Natjecaj initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createNatjecajFormGroup(sampleWithNewData);

        const natjecaj = service.getNatjecaj(formGroup) as any;

        expect(natjecaj).toMatchObject(sampleWithNewData);
      });

      it('should return NewNatjecaj for empty Natjecaj initial value', () => {
        const formGroup = service.createNatjecajFormGroup();

        const natjecaj = service.getNatjecaj(formGroup) as any;

        expect(natjecaj).toMatchObject({});
      });

      it('should return INatjecaj', () => {
        const formGroup = service.createNatjecajFormGroup(sampleWithRequiredData);

        const natjecaj = service.getNatjecaj(formGroup) as any;

        expect(natjecaj).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing INatjecaj should not enable id FormControl', () => {
        const formGroup = service.createNatjecajFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewNatjecaj should disable id FormControl', () => {
        const formGroup = service.createNatjecajFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
