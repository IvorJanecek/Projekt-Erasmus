import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IMobilnost } from '../mobilnost.model';
import { MobilnostService } from '../service/mobilnost.service';

@Injectable({ providedIn: 'root' })
export class MobilnostRoutingResolveService implements Resolve<IMobilnost | null> {
  constructor(protected service: MobilnostService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMobilnost | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((mobilnost: HttpResponse<IMobilnost>) => {
          if (mobilnost.body) {
            return of(mobilnost.body);
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
