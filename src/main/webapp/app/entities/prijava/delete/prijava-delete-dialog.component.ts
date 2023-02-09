import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPrijava } from '../prijava.model';
import { PrijavaService } from '../service/prijava.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './prijava-delete-dialog.component.html',
})
export class PrijavaDeleteDialogComponent {
  prijava?: IPrijava;

  constructor(protected prijavaService: PrijavaService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.prijavaService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
