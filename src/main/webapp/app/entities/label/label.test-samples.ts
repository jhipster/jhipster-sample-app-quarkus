import { ILabel, NewLabel } from './label.model';

export const sampleWithRequiredData: ILabel = {
  id: 21336,
  label: 'gasp',
};

export const sampleWithPartialData: ILabel = {
  id: 5200,
  label: 'mmm',
};

export const sampleWithFullData: ILabel = {
  id: 13098,
  label: 'madly offensively beside',
};

export const sampleWithNewData: NewLabel = {
  label: 'physically at',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
