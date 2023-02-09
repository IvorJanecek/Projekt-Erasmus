import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { INatjecaj } from '../natjecaj.model';
import { NatjecajService } from '../service/natjecaj.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './natjecaj-delete-dialog.component.html',
})
export class NatjecajDeleteDialogComponent {
  natjecaj?: INatjecaj;

  constructor(protected natjecajService: NatjecajService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.natjecajService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
