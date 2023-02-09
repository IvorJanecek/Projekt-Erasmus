import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IFakultet, NewFakultet } from '../fakultet.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IFakultet for edit and NewFakultetFormGroupInput for create.
 */
type FakultetFormGroupInput = IFakultet | PartialWithRequiredKeyOf<NewFakultet>;

type FakultetFormDefaults = Pick<NewFakultet, 'id'>;

type FakultetFormGroupContent = {
  id: FormControl<IFakultet['id'] | NewFakultet['id']>;
  name: FormControl<IFakultet['name']>;
  zemlja: FormControl<IFakultet['zemlja']>;
  grad: FormControl<IFakultet['grad']>;
  adresa: FormControl<IFakultet['adresa']>;
};

export type FakultetFormGroup = FormGroup<FakultetFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class FakultetFormService {
  createFakultetFormGroup(fakultet: FakultetFormGroupInput = { id: null }): FakultetFormGroup {
    const fakultetRawValue = {
      ...this.getFormDefaults(),
      ...fakultet,
    };
    return new FormGroup<FakultetFormGroupContent>({
      id: new FormControl(
        { value: fakultetRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(fakultetRawValue.name, {
        validators: [Validators.required],
      }),
      zemlja: new FormControl(fakultetRawValue.zemlja, {
        validators: [Validators.required],
      }),
      grad: new FormControl(fakultetRawValue.grad, {
        validators: [Validators.required],
      }),
      adresa: new FormControl(fakultetRawValue.adresa, {
        validators: [Validators.required],
      }),
    });
  }

  getFakultet(form: FakultetFormGroup): IFakultet | NewFakultet {
    return form.getRawValue() as IFakultet | NewFakultet;
  }

  resetForm(form: FakultetFormGroup, fakultet: FakultetFormGroupInput): void {
    const fakultetRawValue = { ...this.getFormDefaults(), ...fakultet };
    form.reset(
      {
        ...fakultetRawValue,
        id: { value: fakultetRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): FakultetFormDefaults {
    return {
      id: null,
    };
  }
}
