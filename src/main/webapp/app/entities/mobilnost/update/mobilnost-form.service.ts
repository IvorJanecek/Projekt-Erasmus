import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IMobilnost, NewMobilnost } from '../mobilnost.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IMobilnost for edit and NewMobilnostFormGroupInput for create.
 */
type MobilnostFormGroupInput = IMobilnost | PartialWithRequiredKeyOf<NewMobilnost>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IMobilnost | NewMobilnost> = Omit<T, 'createdDate'> & {
  createdDate?: string | null;
};

type MobilnostFormRawValue = FormValueOf<IMobilnost>;

type NewMobilnostFormRawValue = FormValueOf<NewMobilnost>;

type MobilnostFormDefaults = Pick<NewMobilnost, 'id' | 'createdDate'>;

type MobilnostFormGroupContent = {
  id: FormControl<MobilnostFormRawValue['id'] | NewMobilnost['id']>;
  mobilnostName: FormControl<MobilnostFormRawValue['mobilnostName']>;
  createdDate: FormControl<MobilnostFormRawValue['createdDate']>;
  data: FormControl<MobilnostFormRawValue['data']>;
  dataContentType: FormControl<MobilnostFormRawValue['dataContentType']>;
  trajanjeOd: FormControl<MobilnostFormRawValue['trajanjeOd']>;
  trajanjeDo: FormControl<MobilnostFormRawValue['trajanjeDo']>;
  natjecaj: FormControl<MobilnostFormRawValue['natjecaj']>;
  prijava: FormControl<MobilnostFormRawValue['prijava']>;
};

export type MobilnostFormGroup = FormGroup<MobilnostFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class MobilnostFormService {
  createMobilnostFormGroup(mobilnost: MobilnostFormGroupInput = { id: null }): MobilnostFormGroup {
    const mobilnostRawValue = this.convertMobilnostToMobilnostRawValue({
      ...this.getFormDefaults(),
      ...mobilnost,
    });
    return new FormGroup<MobilnostFormGroupContent>({
      id: new FormControl(
        { value: mobilnostRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      mobilnostName: new FormControl(mobilnostRawValue.mobilnostName, {
        validators: [Validators.required],
      }),
      trajanjeOd: new FormControl(mobilnostRawValue.trajanjeOd, {
        validators: [Validators.required],
      }),
      trajanjeDo: new FormControl(mobilnostRawValue.trajanjeDo, {
        validators: [Validators.required],
      }),
      createdDate: new FormControl(mobilnostRawValue.createdDate),
      data: new FormControl(mobilnostRawValue.data),
      dataContentType: new FormControl(mobilnostRawValue.dataContentType),
      natjecaj: new FormControl(mobilnostRawValue.natjecaj),
      prijava: new FormControl(mobilnostRawValue.prijava),
    });
  }

  getMobilnost(form: MobilnostFormGroup): IMobilnost | NewMobilnost {
    return this.convertMobilnostRawValueToMobilnost(form.getRawValue() as MobilnostFormRawValue | NewMobilnostFormRawValue);
  }

  resetForm(form: MobilnostFormGroup, mobilnost: MobilnostFormGroupInput): void {
    const mobilnostRawValue = this.convertMobilnostToMobilnostRawValue({ ...this.getFormDefaults(), ...mobilnost });
    form.reset(
      {
        ...mobilnostRawValue,
        id: { value: mobilnostRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): MobilnostFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      createdDate: currentTime,
    };
  }

  private convertMobilnostRawValueToMobilnost(rawMobilnost: MobilnostFormRawValue | NewMobilnostFormRawValue): IMobilnost | NewMobilnost {
    return {
      ...rawMobilnost,
      createdDate: dayjs(rawMobilnost.createdDate, DATE_TIME_FORMAT),
    };
  }

  private convertMobilnostToMobilnostRawValue(
    mobilnost: IMobilnost | (Partial<NewMobilnost> & MobilnostFormDefaults)
  ): MobilnostFormRawValue | PartialWithRequiredKeyOf<NewMobilnostFormRawValue> {
    return {
      ...mobilnost,
      createdDate: mobilnost.createdDate ? mobilnost.createdDate.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
