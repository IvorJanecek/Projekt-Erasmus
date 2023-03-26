import { IPrijava } from './prijava.model';

export interface IUploadFile {
  id: number;
  fileName: string | null;
  data?: string | null;
  fileType?: string | null;
  prijava?: Pick<IPrijava, 'id'> | null;
}

export type NewUploadFile = Omit<IUploadFile, 'id'> & { id: null };
