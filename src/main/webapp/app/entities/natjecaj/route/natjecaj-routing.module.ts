import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { NatjecajComponent } from '../list/natjecaj.component';
import { NatjecajDetailComponent } from '../detail/natjecaj-detail.component';
import { NatjecajUpdateComponent } from '../update/natjecaj-update.component';
import { NatjecajRoutingResolveService } from './natjecaj-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const natjecajRoute: Routes = [
  {
    path: '',
    component: NatjecajComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: NatjecajDetailComponent,
    resolve: {
      natjecaj: NatjecajRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: NatjecajUpdateComponent,
    resolve: {
      natjecaj: NatjecajRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: NatjecajUpdateComponent,
    resolve: {
      natjecaj: NatjecajRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(natjecajRoute)],
  exports: [RouterModule],
})
export class NatjecajRoutingModule {}
