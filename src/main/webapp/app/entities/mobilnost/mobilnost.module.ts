import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { MobilnostComponent } from './list/mobilnost.component';
import { MobilnostDetailComponent } from './detail/mobilnost-detail.component';
import { MobilnostUpdateComponent } from './update/mobilnost-update.component';
import { MobilnostDeleteDialogComponent } from './delete/mobilnost-delete-dialog.component';
import { MobilnostRoutingModule } from './route/mobilnost-routing.module';
import { UploadComponent } from './detail/upload/upload.component';

@NgModule({
  imports: [SharedModule, MobilnostRoutingModule],
  declarations: [MobilnostComponent, MobilnostDetailComponent, MobilnostUpdateComponent, MobilnostDeleteDialogComponent, UploadComponent],
})
export class MobilnostModule {}
