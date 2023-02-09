import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { INatjecaj } from '../natjecaj.model';
import { NatjecajService } from '../service/natjecaj.service';

@Injectable({ providedIn: 'root' })
export class NatjecajRoutingResolveService implements Resolve<INatjecaj | null> {
  constructor(protected service: NatjecajService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<INatjecaj | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((natjecaj: HttpResponse<INatjecaj>) => {
          if (natjecaj.body) {
            return of(natjecaj.body);
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
