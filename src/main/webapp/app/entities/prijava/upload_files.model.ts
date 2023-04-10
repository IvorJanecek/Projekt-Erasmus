import { IMobilnost } from '../mobilnost/mobilnost.model';
import { IPrijava } from './prijava.model';

export interface IUploadFile {
  id: number;
  fileName: string | null;
  data?: string | null;
  fileType?: string | null;
  prijava?: Pick<IPrijava, 'id'> | null;
  mobilnost?: Pick<IMobilnost, 'id'> | null;
}

export type NewUploadFile = Omit<IUploadFile, 'id'> & { id: null };
