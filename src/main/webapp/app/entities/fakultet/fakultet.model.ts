export interface IFakultet {
  id: number;
  name?: string | null;
  zemlja?: string | null;
  grad?: string | null;
  adresa?: string | null;
}

export type NewFakultet = Omit<IFakultet, 'id'> & { id: null };
