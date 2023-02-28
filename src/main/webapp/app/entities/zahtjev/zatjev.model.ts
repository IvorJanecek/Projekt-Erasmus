export interface IZahtjev {
  id: number;
  name?: string | null;
}

export type NewZahtjev = Omit<IZahtjev, 'id'> & { id: null };
