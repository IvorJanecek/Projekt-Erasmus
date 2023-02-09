import dayjs from 'dayjs/esm';

import { Kategorija } from 'app/entities/enumerations/kategorija.model';

import { IPrijava, NewPrijava } from './prijava.model';

export const sampleWithRequiredData: IPrijava = {
  id: 4164,
  prijavaName: 'Turkey Cambridgeshire',
  opis: 'Burundi Outdoors Corporate',
  trajanjeOd: dayjs('2022-12-17'),
  trajanjeDo: dayjs('2022-12-18'),
};

export const sampleWithPartialData: IPrijava = {
  id: 46636,
  prijavaName: 'Rubber plum connecting',
  opis: 'Account',
  prihvacen: true,
  trajanjeOd: dayjs('2022-12-17'),
  trajanjeDo: dayjs('2022-12-18'),
  kategorija: Kategorija['STRUCNO_PREDAVANJE'],
};

export const sampleWithFullData: IPrijava = {
  id: 54523,
  prijavaName: 'Mall web-readiness',
  opis: 'green Handmade',
  createdDate: dayjs('2022-12-18T04:41'),
  prihvacen: true,
  trajanjeOd: dayjs('2022-12-17'),
  trajanjeDo: dayjs('2022-12-18'),
  data: '../fake-data/blob/hipster.png',
  dataContentType: 'unknown',
  kategorija: Kategorija['STRUCNO_PREDAVANJE'],
};

export const sampleWithNewData: NewPrijava = {
  prijavaName: 'Licensed installation networks',
  opis: 'Account',
  trajanjeOd: dayjs('2022-12-18'),
  trajanjeDo: dayjs('2022-12-17'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
