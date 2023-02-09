import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IMobilnost } from '../mobilnost.model';
import { MobilnostService } from '../service/mobilnost.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './mobilnost-delete-dialog.component.html',
})
export class MobilnostDeleteDialogComponent {
  mobilnost?: IMobilnost;

  constructor(protected mobilnostService: MobilnostService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.mobilnostService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
