import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { FakultetComponent } from './list/fakultet.component';
import { FakultetDetailComponent } from './detail/fakultet-detail.component';
import { FakultetUpdateComponent } from './update/fakultet-update.component';
import { FakultetDeleteDialogComponent } from './delete/fakultet-delete-dialog.component';
import { FakultetRoutingModule } from './route/fakultet-routing.module';

@NgModule({
  imports: [SharedModule, FakultetRoutingModule],
  declarations: [FakultetComponent, FakultetDetailComponent, FakultetUpdateComponent, FakultetDeleteDialogComponent],
})
export class FakultetModule {}
