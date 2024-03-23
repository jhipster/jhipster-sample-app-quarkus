export interface ILabel {
  id: number;
  label?: string | null;
}

export type NewLabel = Omit<ILabel, 'id'> & { id: null };
