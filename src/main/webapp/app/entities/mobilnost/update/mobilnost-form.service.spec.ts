import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../mobilnost.test-samples';

import { MobilnostFormService } from './mobilnost-form.service';

describe('Mobilnost Form Service', () => {
  let service: MobilnostFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MobilnostFormService);
  });

  describe('Service methods', () => {
    describe('createMobilnostFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createMobilnostFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            mobilnostName: expect.any(Object),
            description: expect.any(Object),
            createdDate: expect.any(Object),
            data: expect.any(Object),
            natjecaj: expect.any(Object),
            prijava: expect.any(Object),
          })
        );
      });

      it('passing IMobilnost should create a new form with FormGroup', () => {
        const formGroup = service.createMobilnostFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            mobilnostName: expect.any(Object),
            description: expect.any(Object),
            createdDate: expect.any(Object),
            data: expect.any(Object),
            natjecaj: expect.any(Object),
            prijava: expect.any(Object),
          })
        );
      });
    });

    describe('getMobilnost', () => {
      it('should return NewMobilnost for default Mobilnost initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createMobilnostFormGroup(sampleWithNewData);

        const mobilnost = service.getMobilnost(formGroup) as any;

        expect(mobilnost).toMatchObject(sampleWithNewData);
      });

      it('should return NewMobilnost for empty Mobilnost initial value', () => {
        const formGroup = service.createMobilnostFormGroup();

        const mobilnost = service.getMobilnost(formGroup) as any;

        expect(mobilnost).toMatchObject({});
      });

      it('should return IMobilnost', () => {
        const formGroup = service.createMobilnostFormGroup(sampleWithRequiredData);

        const mobilnost = service.getMobilnost(formGroup) as any;

        expect(mobilnost).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IMobilnost should not enable id FormControl', () => {
        const formGroup = service.createMobilnostFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewMobilnost should disable id FormControl', () => {
        const formGroup = service.createMobilnostFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
