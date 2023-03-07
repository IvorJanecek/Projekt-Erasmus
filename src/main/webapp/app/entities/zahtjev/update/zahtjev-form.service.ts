import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { IZahtjev, NewZahtjev } from '../zatjev.model';

type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

type ZahtjevFormGroupInput = IZahtjev | PartialWithRequiredKeyOf<NewZahtjev>;

type ZahtjevFormDefaults = Pick<NewZahtjev, 'id'>;

type ZahtjevFormGroupContent = {
  id: FormControl<IZahtjev['id'] | NewZahtjev['id']>;
  name: FormControl<IZahtjev['name']>;
  natjecaj: FormControl<IZahtjev['natjecaj']>;
};

export type ZahtjevFormGroup = FormGroup<ZahtjevFormGroupContent>;

@Injectable({
  providedIn: 'root',
})
export class ZahtjevFormService {
  createZahtjevFormGroup(zahtjev: ZahtjevFormGroupInput = { id: null }): ZahtjevFormGroup {
    const zahtjevRawValue = {
      ...this.getFormDefaults(),
      ...zahtjev,
    };
    return new FormGroup<ZahtjevFormGroupContent>({
      id: new FormControl(
        { value: zahtjevRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(zahtjevRawValue.name, {
        validators: [Validators.required],
      }),
      natjecaj: new FormControl(zahtjevRawValue.natjecaj),
    });
  }

  getZahtjev(form: ZahtjevFormGroup): IZahtjev | NewZahtjev {
    return form.getRawValue() as IZahtjev | NewZahtjev;
  }

  resetForm(form: ZahtjevFormGroup, zahtjev: ZahtjevFormGroupInput): void {
    const zahtjevRawValue = { ...this.getFormDefaults(), ...zahtjev };
    form.reset(
      {
        ...zahtjevRawValue,
        id: { value: zahtjevRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ZahtjevFormDefaults {
    return {
      id: null,
    };
  }
}
