import { Component, Input, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ZahtjevService } from 'app/entities/zahtjev/service/zahtjev.service';
import { INatjecaj } from '../../natjecaj.model';

@Component({
  selector: 'jhi-zahtjev-modal',
  templateUrl: './zahtjev-modal.component.html',
  styleUrls: ['./zahtjev-modal.component.scss'],
})
export class ZahtjevModalComponent implements OnInit {
  numberOfEntities: number | null = null;

  constructor(private zahtjevService: ZahtjevService, public activeModal: NgbActiveModal) {}

  ngOnInit(): void {}

  @Input() natjecaj: INatjecaj | undefined;
}
