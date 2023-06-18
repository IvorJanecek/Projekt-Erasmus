import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators, ValidatorFn, AbstractControl, ValidationErrors } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { INatjecaj, NewNatjecaj } from '../natjecaj.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts INatjecaj for edit and NewNatjecajFormGroupInput for create.
 */
type NatjecajFormGroupInput = INatjecaj | PartialWithRequiredKeyOf<NewNatjecaj>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends INatjecaj | NewNatjecaj> = Omit<T, 'createDate'> & {
  createDate?: string | null;
};

type NatjecajFormRawValue = FormValueOf<INatjecaj>;

type NewNatjecajFormRawValue = FormValueOf<NewNatjecaj>;

type NatjecajFormDefaults = Pick<NewNatjecaj, 'id' | 'createDate'>;

type NatjecajFormGroupContent = {
  id: FormControl<NatjecajFormRawValue['id'] | NewNatjecaj['id']>;
  name: FormControl<NatjecajFormRawValue['name']>;
  description: FormControl<NatjecajFormRawValue['description']>;
  createDate: FormControl<NatjecajFormRawValue['createDate']>;
  datumOd: FormControl<NatjecajFormRawValue['datumOd']>;
  datumDo: FormControl<NatjecajFormRawValue['datumDo']>;
  status: FormControl<NatjecajFormRawValue['status']>;
  korisnik: FormControl<NatjecajFormRawValue['korisnik']>;
};

export type NatjecajFormGroup = FormGroup<NatjecajFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class NatjecajFormService {
  createNatjecajFormGroup(natjecaj: NatjecajFormGroupInput = { id: null }): NatjecajFormGroup {
    const natjecajRawValue = this.convertNatjecajToNatjecajRawValue({
      ...this.getFormDefaults(),
      ...natjecaj,
    });

    const dateValidator: ValidatorFn = (control: AbstractControl): ValidationErrors | null => {
      const trajanjeOd = control.parent?.get('datumOd')?.value;
      const trajanjeDo = control.parent?.get('datumDo')?.value;
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

    return new FormGroup<NatjecajFormGroupContent>({
      id: new FormControl(
        { value: natjecajRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(natjecajRawValue.name, {
        validators: [Validators.required, Validators.maxLength(100)],
      }),
      description: new FormControl(natjecajRawValue.description, {
        validators: [Validators.required, Validators.maxLength(250)],
      }),
      createDate: new FormControl(natjecajRawValue.createDate, {
        validators: [Validators.required],
      }),
      datumOd: new FormControl(natjecajRawValue.datumOd, {
        validators: [Validators.required, dateValidator],
      }),
      datumDo: new FormControl(natjecajRawValue.datumDo, {
        validators: [Validators.required, dateValidator],
      }),
      status: new FormControl(natjecajRawValue.status),
      korisnik: new FormControl(natjecajRawValue.korisnik),
    });
  }

  getNatjecaj(form: NatjecajFormGroup): INatjecaj | NewNatjecaj {
    return this.convertNatjecajRawValueToNatjecaj(form.getRawValue() as NatjecajFormRawValue | NewNatjecajFormRawValue);
  }

  resetForm(form: NatjecajFormGroup, natjecaj: NatjecajFormGroupInput): void {
    const natjecajRawValue = this.convertNatjecajToNatjecajRawValue({ ...this.getFormDefaults(), ...natjecaj });
    form.reset(
      {
        ...natjecajRawValue,
        id: { value: natjecajRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  dateLessThan(from: string, to: string) {
    return (group: FormGroup): { [key: string]: any } => {
      let f = group.controls[from];
      let t = group.controls[to];
      if (f.value > t.value) {
        return {
          dates: 'Date from should be less than Date to',
        };
      }
      return {};
    };
  }

  private getFormDefaults(): NatjecajFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      createDate: currentTime,
    };
  }

  private convertNatjecajRawValueToNatjecaj(rawNatjecaj: NatjecajFormRawValue | NewNatjecajFormRawValue): INatjecaj | NewNatjecaj {
    return {
      ...rawNatjecaj,
      createDate: dayjs(rawNatjecaj.createDate, DATE_TIME_FORMAT),
    };
  }

  private convertNatjecajToNatjecajRawValue(
    natjecaj: INatjecaj | (Partial<NewNatjecaj> & NatjecajFormDefaults)
  ): NatjecajFormRawValue | PartialWithRequiredKeyOf<NewNatjecajFormRawValue> {
    return {
      ...natjecaj,
      createDate: natjecaj.createDate ? natjecaj.createDate.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
