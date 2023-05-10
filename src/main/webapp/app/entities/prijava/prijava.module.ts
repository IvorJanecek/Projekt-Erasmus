import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PrijavaComponent } from './list/prijava.component';
import { PrijavaDetailComponent } from './detail/prijava-detail.component';
import { PrijavaUpdateComponent } from './update/prijava-update.component';
import { PrijavaDeleteDialogComponent } from './delete/prijava-delete-dialog.component';
import { PrijavaRoutingModule } from './route/prijava-routing.module';
import { UploadFilesComponent } from './update/upload-files/upload-files.component';
import { ListAdminComponent } from './list-admin/list-admin.component';
import { FileModalComponent } from './detail/file-modal/file-modal.component';

@NgModule({
  imports: [SharedModule, PrijavaRoutingModule],
  declarations: [
    PrijavaComponent,
    PrijavaDetailComponent,
    PrijavaUpdateComponent,
    PrijavaDeleteDialogComponent,
    UploadFilesComponent,
    ListAdminComponent,
    FileModalComponent,
  ],
})
export class PrijavaModule {}
