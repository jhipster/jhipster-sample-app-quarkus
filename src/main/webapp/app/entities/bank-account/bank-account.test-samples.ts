import { IBankAccount, NewBankAccount } from './bank-account.model';

export const sampleWithRequiredData: IBankAccount = {
  id: 21293,
  name: 'dismember benchmark stunning',
  balance: 1743.91,
};

export const sampleWithPartialData: IBankAccount = {
  id: 5568,
  name: 'wish concerning duh',
  balance: 7602.71,
};

export const sampleWithFullData: IBankAccount = {
  id: 30005,
  name: 'nor gee',
  balance: 31242.73,
};

export const sampleWithNewData: NewBankAccount = {
  name: 'lightly excited pupate',
  balance: 29314.16,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
