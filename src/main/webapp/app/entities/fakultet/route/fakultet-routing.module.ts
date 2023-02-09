import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { FakultetComponent } from '../list/fakultet.component';
import { FakultetDetailComponent } from '../detail/fakultet-detail.component';
import { FakultetUpdateComponent } from '../update/fakultet-update.component';
import { FakultetRoutingResolveService } from './fakultet-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const fakultetRoute: Routes = [
  {
    path: '',
    component: FakultetComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FakultetDetailComponent,
    resolve: {
      fakultet: FakultetRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FakultetUpdateComponent,
    resolve: {
      fakultet: FakultetRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FakultetUpdateComponent,
    resolve: {
      fakultet: FakultetRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(fakultetRoute)],
  exports: [RouterModule],
})
export class FakultetRoutingModule {}
