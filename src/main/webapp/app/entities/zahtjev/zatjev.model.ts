import { INatjecaj } from '../natjecaj/natjecaj.model';

export interface IZahtjev {
  id: number;
  name?: string | null;
  natjecaj?: Pick<INatjecaj, 'id' | 'name'> | null;
}

export type NewZahtjev = Omit<IZahtjev, 'id'> & { id: null };
