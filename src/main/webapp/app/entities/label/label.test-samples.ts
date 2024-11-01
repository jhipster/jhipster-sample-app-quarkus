import { ILabel, NewLabel } from './label.model';

export const sampleWithRequiredData: ILabel = {
  id: 21336,
  label: 'modulo',
};

export const sampleWithPartialData: ILabel = {
  id: 13098,
  label: 'seemingly yippee deceivingly',
};

export const sampleWithFullData: ILabel = {
  id: 12914,
  label: 'optimistic',
};

export const sampleWithNewData: NewLabel = {
  label: 'sundae',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
