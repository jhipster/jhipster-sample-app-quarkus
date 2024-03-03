import dayjs from 'dayjs/esm';

import { IOperation, NewOperation } from './operation.model';

export const sampleWithRequiredData: IOperation = {
  id: 10801,
  date: dayjs('2015-08-04T17:32'),
  amount: 17337.01,
};

export const sampleWithPartialData: IOperation = {
  id: 9648,
  date: dayjs('2015-08-05T12:28'),
  amount: 8055.28,
};

export const sampleWithFullData: IOperation = {
  id: 27922,
  date: dayjs('2015-08-05T10:26'),
  description: 'huzzah',
  amount: 30952.43,
};

export const sampleWithNewData: NewOperation = {
  date: dayjs('2015-08-05T09:08'),
  amount: 29328.01,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
