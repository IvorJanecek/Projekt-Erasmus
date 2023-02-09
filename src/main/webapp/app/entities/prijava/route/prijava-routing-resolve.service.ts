import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPrijava } from '../prijava.model';
import { PrijavaService } from '../service/prijava.service';

@Injectable({ providedIn: 'root' })
export class PrijavaRoutingResolveService implements Resolve<IPrijava | null> {
  constructor(protected service: PrijavaService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPrijava | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((prijava: HttpResponse<IPrijava>) => {
          if (prijava.body) {
            return of(prijava.body);
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
