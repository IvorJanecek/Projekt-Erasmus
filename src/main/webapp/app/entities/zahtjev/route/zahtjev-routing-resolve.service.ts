import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ZahtjevService } from '../service/zahtjev.service';
import { IZahtjev } from '../zatjev.model';

@Injectable({ providedIn: 'root' })
export class ZahtjevRoutingResolveService implements Resolve<IZahtjev | null> {
  constructor(protected service: ZahtjevService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IZahtjev | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((zahtjev: HttpResponse<IZahtjev>) => {
          if (zahtjev.body) {
            return of(zahtjev.body);
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
