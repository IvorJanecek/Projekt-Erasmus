import dayjs from 'dayjs/esm';

import { Status } from 'app/entities/enumerations/status.model';

import { INatjecaj, NewNatjecaj } from './natjecaj.model';

export const sampleWithRequiredData: INatjecaj = {
  id: 79515,
  name: 'Soft Markets EXE',
  description: 'bypassing Riyal SDR',
};

export const sampleWithPartialData: INatjecaj = {
  id: 46509,
  name: 'Avon static Engineer',
  description: 'communities Division',
  datumOd: dayjs('2022-12-18'),
  datumDo: dayjs('2022-12-17'),
};

export const sampleWithFullData: INatjecaj = {
  id: 40658,
  name: 'Handmade cultivate',
  description: 'vortals',
  createDate: dayjs('2022-12-18T02:57'),
  datumOd: dayjs('2022-12-18'),
  datumDo: dayjs('2022-12-17'),
  status: Status['ZATVOREN'],
};

export const sampleWithNewData: NewNatjecaj = {
  name: 'plum Cheese',
  description: 'Euro Home syndicate',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
