import dayjs from 'dayjs/esm';

import { IOperation, NewOperation } from './operation.model';

export const sampleWithRequiredData: IOperation = {
  id: 10801,
  date: dayjs('2015-08-05T01:32'),
  amount: 9647.73,
};

export const sampleWithPartialData: IOperation = {
  id: 3284,
  date: dayjs('2015-08-04T21:15'),
  description: 'needily',
  amount: 23767.92,
};

export const sampleWithFullData: IOperation = {
  id: 19709,
  date: dayjs('2015-08-05T05:09'),
  description: 'now',
  amount: 18598.79,
};

export const sampleWithNewData: NewOperation = {
  date: dayjs('2015-08-04T20:01'),
  amount: 26775.88,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
