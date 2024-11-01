import { IBankAccount, NewBankAccount } from './bank-account.model';

export const sampleWithRequiredData: IBankAccount = {
  id: 21293,
  name: 'square',
  balance: 21123.34,
};

export const sampleWithPartialData: IBankAccount = {
  id: 13794,
  name: 'crest within unzip',
  balance: 18891.85,
};

export const sampleWithFullData: IBankAccount = {
  id: 6209,
  name: 'opposite overtrain furthermore',
  balance: 817.26,
};

export const sampleWithNewData: NewBankAccount = {
  name: 'veg more lamp',
  balance: 12788.52,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
