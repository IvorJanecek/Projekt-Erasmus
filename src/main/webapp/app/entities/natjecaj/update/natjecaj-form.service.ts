import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

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
};

export type NatjecajFormGroup = FormGroup<NatjecajFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class NatjecajFormService {
  createNatjecajFormGroup(natjecaj: NatjecajFormGroupInput = { id: null }): NatjecajFormGroup {
    const natjecajRawValue = this.convertNatjecajToNatjecajRawValue({
      ...this.getFormDefaults(),
      ...natjecaj,
    });
    return new FormGroup<NatjecajFormGroupContent>({
      id: new FormControl(
        { value: natjecajRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(natjecajRawValue.name, {
        validators: [Validators.required],
      }),
      description: new FormControl(natjecajRawValue.description, {
        validators: [Validators.required],
      }),
      createDate: new FormControl(natjecajRawValue.createDate),
      datumOd: new FormControl(natjecajRawValue.datumOd),
      datumDo: new FormControl(natjecajRawValue.datumDo),
      status: new FormControl(natjecajRawValue.status),
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
