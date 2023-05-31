import { IMobilnost } from '../mobilnost/mobilnost.model';

export interface IUploadFileAdmin {
  id: number;
  fileName: string | null;
  data?: string | null;
  fileType?: string | null;
  mobilnost?: Pick<IMobilnost, 'id'> | null;
}

export type NewUploadFileAdmin = Omit<IUploadFileAdmin, 'id'> & { id: null };
