import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IFakultet } from '../fakultet.model';
import { FakultetService } from '../service/fakultet.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './fakultet-delete-dialog.component.html',
})
export class FakultetDeleteDialogComponent {
  fakultet?: IFakultet;

  constructor(protected fakultetService: FakultetService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.fakultetService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
