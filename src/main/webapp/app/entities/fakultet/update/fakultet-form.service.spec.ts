import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../fakultet.test-samples';

import { FakultetFormService } from './fakultet-form.service';

describe('Fakultet Form Service', () => {
  let service: FakultetFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FakultetFormService);
  });

  describe('Service methods', () => {
    describe('createFakultetFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createFakultetFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            zemlja: expect.any(Object),
            grad: expect.any(Object),
            adresa: expect.any(Object),
          })
        );
      });

      it('passing IFakultet should create a new form with FormGroup', () => {
        const formGroup = service.createFakultetFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            zemlja: expect.any(Object),
            grad: expect.any(Object),
            adresa: expect.any(Object),
          })
        );
      });
    });

    describe('getFakultet', () => {
      it('should return NewFakultet for default Fakultet initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createFakultetFormGroup(sampleWithNewData);

        const fakultet = service.getFakultet(formGroup) as any;

        expect(fakultet).toMatchObject(sampleWithNewData);
      });

      it('should return NewFakultet for empty Fakultet initial value', () => {
        const formGroup = service.createFakultetFormGroup();

        const fakultet = service.getFakultet(formGroup) as any;

        expect(fakultet).toMatchObject({});
      });

      it('should return IFakultet', () => {
        const formGroup = service.createFakultetFormGroup(sampleWithRequiredData);

        const fakultet = service.getFakultet(formGroup) as any;

        expect(fakultet).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IFakultet should not enable id FormControl', () => {
        const formGroup = service.createFakultetFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewFakultet should disable id FormControl', () => {
        const formGroup = service.createFakultetFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
