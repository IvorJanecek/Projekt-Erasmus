import dayjs from 'dayjs/esm';
import { IUser } from 'app/entities/user/user.model';
import { IFakultet } from 'app/entities/fakultet/fakultet.model';
import { INatjecaj } from 'app/entities/natjecaj/natjecaj.model';
import { Kategorija } from 'app/entities/enumerations/kategorija.model';
import { IUploadFile } from './upload_files.model';
import { StatusPrijave } from '../enumerations/statusprijave.mode';

export interface IPrijava {
  id: number;
  prijavaName?: string | null;
  createdDate?: dayjs.Dayjs | null;
  trajanjeOd?: dayjs.Dayjs | null;
  trajanjeDo?: dayjs.Dayjs | null;
  data?: string | null;
  dataContentType?: string | null;
  kategorija?: Kategorija | null;
  user?: Pick<IUser, 'id' | 'login'> | null;
  fakultet?: Pick<IFakultet, 'id' | 'name'> | null;
  natjecaj?: Pick<INatjecaj, 'id' | 'name'> | null;
  uploadFiles?: IUploadFile[];
  statusPrijave?: StatusPrijave | null;
}

export type NewPrijava = Omit<IPrijava, 'id'> & { id: null };
