import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PrijavaComponent } from '../list/prijava.component';
import { PrijavaDetailComponent } from '../detail/prijava-detail.component';
import { PrijavaUpdateComponent } from '../update/prijava-update.component';
import { PrijavaRoutingResolveService } from './prijava-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';
import { UploadFilesComponent } from '../update/upload-files/upload-files.component';
import { ListAdminComponent } from '../list-admin/list-admin.component';
import { EditPrijavaComponent } from '../edit-prijava/edit-prijava.component';

const prijavaRoute: Routes = [
  {
    path: '',
    component: PrijavaComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'list-admin',
    component: ListAdminComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PrijavaDetailComponent,
    resolve: {
      prijava: PrijavaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'upload-files/:prijavaId',
    component: UploadFilesComponent,
    resolve: {
      prijava: PrijavaRoutingResolveService,
    },
  },
  {
    path: 'new',
    component: PrijavaUpdateComponent,
    resolve: {
      prijava: PrijavaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EditPrijavaComponent,
    resolve: {
      prijava: PrijavaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(prijavaRoute)],
  exports: [RouterModule],
})
export class PrijavaRoutingModule {}
