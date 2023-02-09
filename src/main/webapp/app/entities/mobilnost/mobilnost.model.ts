import dayjs from 'dayjs/esm';
import { INatjecaj } from 'app/entities/natjecaj/natjecaj.model';
import { IPrijava } from 'app/entities/prijava/prijava.model';

export interface IMobilnost {
  id: number;
  mobilnostName?: string | null;
  description?: string | null;
  createdDate?: dayjs.Dayjs | null;
  data?: string | null;
  dataContentType?: string | null;
  natjecaj?: Pick<INatjecaj, 'id' | 'name'> | null;
  prijava?: Pick<IPrijava, 'id' | 'prijavaName'> | null;
}

export type NewMobilnost = Omit<IMobilnost, 'id'> & { id: null };
