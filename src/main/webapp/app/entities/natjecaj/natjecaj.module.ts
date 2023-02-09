import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { NatjecajComponent } from './list/natjecaj.component';
import { NatjecajDetailComponent } from './detail/natjecaj-detail.component';
import { NatjecajUpdateComponent } from './update/natjecaj-update.component';
import { NatjecajDeleteDialogComponent } from './delete/natjecaj-delete-dialog.component';
import { NatjecajRoutingModule } from './route/natjecaj-routing.module';

@NgModule({
  imports: [SharedModule, NatjecajRoutingModule],
  declarations: [NatjecajComponent, NatjecajDetailComponent, NatjecajUpdateComponent, NatjecajDeleteDialogComponent],
})
export class NatjecajModule {}
