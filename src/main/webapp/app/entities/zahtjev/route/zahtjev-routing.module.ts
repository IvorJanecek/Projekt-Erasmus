import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ZahtjevComponent } from '../list/zahtjev.component';
import { ASC } from 'app/config/navigation.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';

import { ZahtjevUpdateComponent } from '../update/zahtjev-update.component';
import { ZahtjevRoutingResolveService } from './zahtjev-routing-resolve.service';
import { ZahtjevDetailComponent } from '../detail/zahtjev-detail.component';

const zahtjevRoute: Routes = [
  {
    path: '',
    component: ZahtjevComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ZahtjevDetailComponent,
    resolve: {
      zahtjev: ZahtjevRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ZahtjevUpdateComponent,
    resolve: {
      zahtjev: ZahtjevRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ZahtjevUpdateComponent,
    resolve: {
      zahtjev: ZahtjevRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(zahtjevRoute)],
  exports: [RouterModule],
})
export class ZahtjevRoutingModule {}
