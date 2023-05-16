import dayjs from 'dayjs/esm';
import { INatjecaj } from 'app/entities/natjecaj/natjecaj.model';
import { IPrijava } from 'app/entities/prijava/prijava.model';
import { IUploadFile } from '../prijava/upload_files.model';
import { IUser } from '../user/user.model';
import { StatusMobilnosti } from '../enumerations/statusmobilnosti.mode';

export interface IMobilnost {
  id: number;
  mobilnostName?: string | null;
  description?: string | null;
  createdDate?: dayjs.Dayjs | null;
  data?: string | null;
  dataContentType?: string | null;
  natjecaj?: Pick<INatjecaj, 'id' | 'name'> | null;
  prijava?: Pick<IPrijava, 'id' | 'prijavaName'> | null;
  uploadFiles?: IUploadFile[];
  user?: Pick<IUser, 'id' | 'login'> | null;
  statusMobilnosti?: StatusMobilnosti | null;
}

export type NewMobilnost = Omit<IMobilnost, 'id'> & { id: null };
