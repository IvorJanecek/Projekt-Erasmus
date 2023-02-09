import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../prijava.test-samples';

import { PrijavaFormService } from './prijava-form.service';

describe('Prijava Form Service', () => {
  let service: PrijavaFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PrijavaFormService);
  });

  describe('Service methods', () => {
    describe('createPrijavaFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPrijavaFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            prijavaName: expect.any(Object),
            opis: expect.any(Object),
            createdDate: expect.any(Object),
            prihvacen: expect.any(Object),
            trajanjeOd: expect.any(Object),
            trajanjeDo: expect.any(Object),
            data: expect.any(Object),
            kategorija: expect.any(Object),
            user: expect.any(Object),
            fakultet: expect.any(Object),
            natjecaj: expect.any(Object),
          })
        );
      });

      it('passing IPrijava should create a new form with FormGroup', () => {
        const formGroup = service.createPrijavaFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            prijavaName: expect.any(Object),
            opis: expect.any(Object),
            createdDate: expect.any(Object),
            prihvacen: expect.any(Object),
            trajanjeOd: expect.any(Object),
            trajanjeDo: expect.any(Object),
            data: expect.any(Object),
            kategorija: expect.any(Object),
            user: expect.any(Object),
            fakultet: expect.any(Object),
            natjecaj: expect.any(Object),
          })
        );
      });
    });

    describe('getPrijava', () => {
      it('should return NewPrijava for default Prijava initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createPrijavaFormGroup(sampleWithNewData);

        const prijava = service.getPrijava(formGroup) as any;

        expect(prijava).toMatchObject(sampleWithNewData);
      });

      it('should return NewPrijava for empty Prijava initial value', () => {
        const formGroup = service.createPrijavaFormGroup();

        const prijava = service.getPrijava(formGroup) as any;

        expect(prijava).toMatchObject({});
      });

      it('should return IPrijava', () => {
        const formGroup = service.createPrijavaFormGroup(sampleWithRequiredData);

        const prijava = service.getPrijava(formGroup) as any;

        expect(prijava).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPrijava should not enable id FormControl', () => {
        const formGroup = service.createPrijavaFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPrijava should disable id FormControl', () => {
        const formGroup = service.createPrijavaFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
