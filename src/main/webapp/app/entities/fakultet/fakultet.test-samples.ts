import { IFakultet, NewFakultet } from './fakultet.model';

export const sampleWithRequiredData: IFakultet = {
  id: 53075,
  name: 'distributed Health circuit',
};

export const sampleWithPartialData: IFakultet = {
  id: 52481,
  name: 'Infrastructure Human',
};

export const sampleWithFullData: IFakultet = {
  id: 90625,
  name: 'Avenue Kuwaiti',
};

export const sampleWithNewData: NewFakultet = {
  name: 'Accountability Unbranded',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
