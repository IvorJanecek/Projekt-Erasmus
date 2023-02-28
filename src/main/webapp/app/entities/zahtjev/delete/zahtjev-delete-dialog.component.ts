import { Component, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { INatjecaj } from 'app/entities/natjecaj/natjecaj.model';
import { NatjecajService } from 'app/entities/natjecaj/service/natjecaj.service';
import { ZahtjevService } from '../service/zahtjev.service';
import { IZahtjev } from '../zatjev.model';

@Component({
  selector: 'jhi-zahtjev-delete-dialog',
  templateUrl: './zahtjev-delete-dialog.component.html',
})
export class ZahtjevDeleteDialogComponent {
  zahtjev?: IZahtjev;

  constructor(protected zahtjevService: ZahtjevService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.zahtjevService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
