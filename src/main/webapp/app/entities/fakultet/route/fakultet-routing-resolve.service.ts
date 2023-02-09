import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IFakultet } from '../fakultet.model';
import { FakultetService } from '../service/fakultet.service';

@Injectable({ providedIn: 'root' })
export class FakultetRoutingResolveService implements Resolve<IFakultet | null> {
  constructor(protected service: FakultetService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFakultet | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((fakultet: HttpResponse<IFakultet>) => {
          if (fakultet.body) {
            return of(fakultet.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
