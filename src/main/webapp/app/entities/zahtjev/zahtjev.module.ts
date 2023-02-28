import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ZahtjevDeleteDialogComponent } from './delete/zahtjev-delete-dialog.component';
import { ZahtjevDetailComponent } from './detail/zahtjev-detail.component';

import { ZahtjevComponent } from './list/zahtjev.component';
import { ZahtjevRoutingModule } from './route/zahtjev-routing.module';
import { ZahtjevUpdateComponent } from './update/zahtjev-update.component';

@NgModule({
  imports: [SharedModule, ZahtjevRoutingModule],
  declarations: [ZahtjevComponent, ZahtjevDetailComponent, ZahtjevUpdateComponent, ZahtjevDeleteDialogComponent],
})
export class ZahtjevModule {}
