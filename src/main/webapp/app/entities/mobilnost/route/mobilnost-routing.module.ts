import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { MobilnostComponent } from '../list/mobilnost.component';
import { MobilnostDetailComponent } from '../detail/mobilnost-detail.component';
import { MobilnostUpdateComponent } from '../update/mobilnost-update.component';
import { MobilnostRoutingResolveService } from './mobilnost-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';
import { UploadComponent } from '../detail/upload/upload.component';
import { UploadAdminComponent } from '../detail/upload-admin/upload-admin.component';

const mobilnostRoute: Routes = [
  {
    path: '',
    component: MobilnostComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MobilnostDetailComponent,
    resolve: {
      mobilnost: MobilnostRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MobilnostUpdateComponent,
    resolve: {
      mobilnost: MobilnostRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MobilnostUpdateComponent,
    resolve: {
      mobilnost: MobilnostRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/upload',
    component: UploadComponent,
    resolve: {
      mobilnost: MobilnostRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/upload-admin',
    component: UploadAdminComponent,
    resolve: {
      mobilnost: MobilnostRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(mobilnostRoute)],
  exports: [RouterModule],
})
export class MobilnostRoutingModule {}
