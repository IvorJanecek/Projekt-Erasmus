/**
 * Angular bootstrap Date adapter
 */
import { Injectable } from '@angular/core';
import { NgbDateAdapter, NgbDateStruct } from '@ng-bootstrap/ng-bootstrap';
import dayjs from 'dayjs/esm';

@Injectable()
export class NgbDateDayjsAdapter extends NgbDateAdapter<dayjs.Dayjs> {
  fromModel(date: dayjs.Dayjs | null): NgbDateStruct | null {
    if (date && dayjs.isDayjs(date) && date.isValid()) {
      return { day: date.date(), month: date.month() + 1, year: date.year() };
    }
    return null;
  }

  toModel(date: NgbDateStruct | null): dayjs.Dayjs | null {
    return date ? dayjs(`${date.day}-${date.month}-${date.year}`) : null;
  }
}
