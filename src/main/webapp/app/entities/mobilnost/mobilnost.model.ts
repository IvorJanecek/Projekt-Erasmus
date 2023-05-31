import dayjs from 'dayjs/esm';
import { INatjecaj } from 'app/entities/natjecaj/natjecaj.model';
import { IPrijava } from 'app/entities/prijava/prijava.model';
import { IUploadFile } from '../prijava/upload_files.model';
import { IUser } from '../user/user.model';
import { StatusMobilnosti } from '../enumerations/statusmobilnosti.mode';
import { IUploadFileAdmin } from './upload_files_admin.model';

export interface IMobilnost {
  id: number;
  mobilnostName?: string | null;
  createdDate?: dayjs.Dayjs | null;
  data?: string | null;
  dataContentType?: string | null;
  trajanjeOd?: dayjs.Dayjs | null;
  trajanjeDo?: dayjs.Dayjs | null;
  natjecaj?: Pick<INatjecaj, 'id' | 'name'> | null;
  prijava?: Pick<IPrijava, 'id' | 'prijavaName'> | null;
  uploadFiles?: IUploadFile[];
  uploadFilesAdmin?: IUploadFileAdmin[];
  user?: Pick<IUser, 'id' | 'login'> | null;
  statusMobilnosti?: StatusMobilnosti | null;
}

export type NewMobilnost = Omit<IMobilnost, 'id'> & { id: null };
