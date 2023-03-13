import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { IPrijava } from 'app/entities/prijava/prijava.model';
import { IZahtjev } from 'app/entities/zahtjev/zatjev.model';

import { INatjecaj } from '../natjecaj.model';
import { NatjecajService } from '../service/natjecaj.service';
import { ZahtjevModalComponent } from './zahtjev-modal/zahtjev-modal.component';

@Component({
  selector: 'jhi-natjecaj-detail',
  templateUrl: './natjecaj-detail.component.html',
})
export class NatjecajDetailComponent implements OnInit {
  natjecaj: INatjecaj | null = null;
  prijavaService: any;
  numZahtjevsToCreate = 1;
  zahtjevs?: IZahtjev[];

  constructor(
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    private modalService: NgbModal,
    private natjecajService: NatjecajService
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ natjecaj }) => {
      this.natjecaj = natjecaj;
    });
  }

  createNewZahtjev(natjecaj: Pick<INatjecaj, 'id'>): void {
    this.numZahtjevsToCreate = 1;
    const modalRef = this.modalService.open(ZahtjevModalComponent, { centered: true });

    modalRef.componentInstance.natjecaj = natjecaj;
    modalRef.result.then(
      yes => {
        console.log('Ok click');
      },
      cancel => {
        console.log('cancel Click');
      }
    );
  }

  createNewPrijava(natjecaj: Pick<INatjecaj, 'id'>): void {
    const newPrijava: IPrijava = {
      natjecaj: natjecaj,
      id: natjecaj.id,
    };
    this.router.navigate(['/prijava/new'], { state: { prijava: newPrijava } });
  }

  previousState(): void {
    window.history.back();
  }
}
