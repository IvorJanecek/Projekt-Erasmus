import dayjs from 'dayjs/esm';

import { IMobilnost, NewMobilnost } from './mobilnost.model';

export const sampleWithRequiredData: IMobilnost = {
  id: 6425,
  mobilnostName: 'National Account PNG',
};

export const sampleWithPartialData: IMobilnost = {
  id: 7577,
  mobilnostName: 'Re-contextualized Global revolutionize',
};

export const sampleWithFullData: IMobilnost = {
  id: 87652,
  mobilnostName: 'Curve monitor Generic',
  createdDate: dayjs('2022-12-18T09:21'),
  data: '../fake-data/blob/hipster.png',
  dataContentType: 'unknown',
};

export const sampleWithNewData: NewMobilnost = {
  mobilnostName: 'Ergonomic Monitored',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
