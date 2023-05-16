import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators, ValidatorFn, AbstractControl, ValidationErrors } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IPrijava, NewPrijava } from '../prijava.model';
import { dateValidator } from './date-range-validator';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPrijava for edit and NewPrijavaFormGroupInput for create.
 */
type PrijavaFormGroupInput = IPrijava | PartialWithRequiredKeyOf<NewPrijava>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IPrijava | NewPrijava> = Omit<T, 'createdDate'> & {
  createdDate?: string | null;
};

type PrijavaFormRawValue = FormValueOf<IPrijava>;

type NewPrijavaFormRawValue = FormValueOf<NewPrijava>;

type PrijavaFormDefaults = Pick<NewPrijava, 'id' | 'createdDate'>;

type PrijavaFormGroupContent = {
  id: FormControl<PrijavaFormRawValue['id'] | NewPrijava['id']>;
  prijavaName: FormControl<PrijavaFormRawValue['prijavaName']>;
  opis: FormControl<PrijavaFormRawValue['opis']>;
  createdDate: FormControl<PrijavaFormRawValue['createdDate']>;
  trajanjeOd: FormControl<PrijavaFormRawValue['trajanjeOd']>;
  trajanjeDo: FormControl<PrijavaFormRawValue['trajanjeDo']>;
  data: FormControl<PrijavaFormRawValue['data']>;
  dataContentType: FormControl<PrijavaFormRawValue['dataContentType']>;
  kategorija: FormControl<PrijavaFormRawValue['kategorija']>;
  user: FormControl<PrijavaFormRawValue['user']>;
  fakultet: FormControl<PrijavaFormRawValue['fakultet']>;
  natjecaj: FormControl<PrijavaFormRawValue['natjecaj']>;
  statusPrijave: FormControl<PrijavaFormRawValue['statusPrijave']>;
};

export type PrijavaFormGroup = FormGroup<PrijavaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PrijavaFormService {
  createPrijavaFormGroup(prijava: PrijavaFormGroupInput = { id: null }): PrijavaFormGroup {
    const prijavaRawValue = this.convertPrijavaToPrijavaRawValue({
      ...this.getFormDefaults(),
      ...prijava,
    });
    const dateValidator: ValidatorFn = (control: AbstractControl): ValidationErrors | null => {
      const trajanjeOd = control.parent?.get('trajanjeOd')?.value;
      const trajanjeDo = control.parent?.get('trajanjeDo')?.value;
      if (!trajanjeOd || !trajanjeDo) {
        return null;
      }
      const start = dayjs(trajanjeOd);
      const end = dayjs(trajanjeDo);
      if (start >= end) {
        return { dateRangeError: true };
      }
      return null;
    };

    return new FormGroup<PrijavaFormGroupContent>({
      id: new FormControl(
        { value: prijavaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      prijavaName: new FormControl(prijavaRawValue.prijavaName, {
        validators: [Validators.required],
      }),
      opis: new FormControl(prijavaRawValue.opis),
      createdDate: new FormControl(prijavaRawValue.createdDate),
      trajanjeOd: new FormControl(prijavaRawValue.trajanjeOd, {
        validators: [Validators.required, dateValidator],
      }),
      trajanjeDo: new FormControl(prijavaRawValue.trajanjeDo, {
        validators: [Validators.required, dateValidator],
      }),
      data: new FormControl(prijavaRawValue.data),
      dataContentType: new FormControl(prijavaRawValue.dataContentType),
      kategorija: new FormControl(prijavaRawValue.kategorija),
      statusPrijave: new FormControl(prijavaRawValue.statusPrijave),
      user: new FormControl(prijavaRawValue.user),
      fakultet: new FormControl(prijavaRawValue.fakultet),
      natjecaj: new FormControl(prijavaRawValue.natjecaj),
    });
  }

  getPrijava(form: PrijavaFormGroup): IPrijava | NewPrijava {
    return this.convertPrijavaRawValueToPrijava(form.getRawValue() as PrijavaFormRawValue | NewPrijavaFormRawValue);
  }

  resetForm(form: PrijavaFormGroup, prijava: PrijavaFormGroupInput): void {
    const prijavaRawValue = this.convertPrijavaToPrijavaRawValue({ ...this.getFormDefaults(), ...prijava });
    form.reset(
      {
        ...prijavaRawValue,
        id: { value: prijavaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): PrijavaFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      createdDate: currentTime,
    };
  }

  private convertPrijavaRawValueToPrijava(rawPrijava: PrijavaFormRawValue | NewPrijavaFormRawValue): IPrijava | NewPrijava {
    return {
      ...rawPrijava,
      createdDate: dayjs(rawPrijava.createdDate, DATE_TIME_FORMAT),
    };
  }

  private convertPrijavaToPrijavaRawValue(
    prijava: IPrijava | (Partial<NewPrijava> & PrijavaFormDefaults)
  ): PrijavaFormRawValue | PartialWithRequiredKeyOf<NewPrijavaFormRawValue> {
    return {
      ...prijava,
      createdDate: prijava.createdDate ? prijava.createdDate.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
