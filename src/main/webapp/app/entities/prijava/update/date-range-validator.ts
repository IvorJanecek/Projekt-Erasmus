import { AbstractControl, FormGroup, ValidationErrors, ValidatorFn } from '@angular/forms';

export const dateValidator: ValidatorFn = (control: AbstractControl): ValidationErrors | null => {
  const trajanjeOd = control.get('trajanjeOd');
  const trajanjeDo = control.get('trajanjeDo');
  if (!trajanjeOd || !trajanjeDo) {
    return null;
  }
  const start = new Date(trajanjeOd.value);
  const end = new Date(trajanjeDo.value);
  if (start >= end) {
    return { dateRangeError: true };
  }
  return null;
};
