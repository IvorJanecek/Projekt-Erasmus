import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { MobilnostComponent } from './list/mobilnost.component';
import { MobilnostDetailComponent } from './detail/mobilnost-detail.component';
import { MobilnostUpdateComponent } from './update/mobilnost-update.component';
import { MobilnostDeleteDialogComponent } from './delete/mobilnost-delete-dialog.component';
import { MobilnostRoutingModule } from './route/mobilnost-routing.module';
import { UploadComponent } from './detail/upload/upload.component';
import { FileModalComponent } from './detail/file-modal/file-modal.component';
import { AdminFileModalComponent } from './detail/admin-file-modal/admin-file-modal.component';
import { UploadAdminComponent } from './detail/upload-admin/upload-admin.component';

@NgModule({
  imports: [SharedModule, MobilnostRoutingModule],
  declarations: [
    MobilnostComponent,
    MobilnostDetailComponent,
    MobilnostUpdateComponent,
    MobilnostDeleteDialogComponent,
    UploadComponent,
    FileModalComponent,
    AdminFileModalComponent,
    UploadAdminComponent,
  ],
})
export class MobilnostModule {}
