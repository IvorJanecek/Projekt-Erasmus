import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IPrijava } from 'app/entities/prijava/prijava.model';
import { ZahtjevDeleteDialogComponent } from 'app/entities/zahtjev/delete/zahtjev-delete-dialog.component';
import { EntityArrayResponseType, ZahtjevService } from 'app/entities/zahtjev/service/zahtjev.service';
import { IZahtjev } from 'app/entities/zahtjev/zatjev.model';
import { SortService } from 'app/shared/sort/sort.service';
import { filter, switchMap } from 'rxjs';

import { INatjecaj } from '../natjecaj.model';
import { NatjecajService } from '../service/natjecaj.service';
import { ZahtjevModalComponent } from './zahtjev-modal/zahtjev-modal.component';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-natjecaj-detail',
  templateUrl: './natjecaj-detail.component.html',
})
export class NatjecajDetailComponent implements OnInit {
  natjecaj: INatjecaj | null = null;
  prijavaService: any;
  numZahtjevsToCreate = 1;
  zahtjevs?: IZahtjev[];
  zahtjev: IZahtjev | null = null;

  constructor(
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    private modalService: NgbModal,
    private natjecajService: NatjecajService,
    protected sortService: SortService,
    private zahtjevService: ZahtjevService,
    protected dataUtils: DataUtils
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ natjecaj }) => {
      this.natjecaj = natjecaj;
    });
  }

  deleteZahtjev(id: number) {
    this.zahtjevService.delete(id).subscribe(
      () => {
        // success: remove the deleted zahtjev from the natjecaj.zahtjevs list
        this.natjecaj!.zahtjevs = this.natjecaj!.zahtjevs?.filter(z => z.id !== id);
      },
      error => {
        // handle error
      }
    );
  }

  onModalHidden(): void {
    window.location.reload();
  }

  urediZahtjev(natjecaj: Pick<INatjecaj, 'id'>, zahtjev: Pick<IZahtjev, 'id' | 'name'>): void {
    const modalRef = this.modalService.open(ZahtjevModalComponent, { centered: true });

    modalRef.componentInstance.zahtjev = zahtjev;
    modalRef.componentInstance.natjecaj = natjecaj;
    modalRef.componentInstance.editModal = true;
    history.state.zahtjev = zahtjev;

    modalRef.result.then(
      yes => {
        console.log('Ok click');
      },
      cancel => {
        console.log('cancel Click');
        window.location.reload();
      }
    );
  }

  createNewZahtjev(natjecaj: Pick<INatjecaj, 'id'>): void {
    const modalRef = this.modalService.open(ZahtjevModalComponent, { centered: true });

    modalRef.componentInstance.natjecaj = natjecaj;
    modalRef.result.then(
      yes => {
        console.log('Ok click');
      },
      cancel => {
        console.log('cancel Click');
        window.location.reload();
      }
    );
  }

  createNewPrijava(natjecaj: Pick<INatjecaj, 'id' | 'name'>): void {
    const newPrijava: IPrijava = {
      natjecaj: natjecaj,
      id: natjecaj.id,
      prijavaName: 'Prijava za ' + natjecaj.name,
    };
    console.log(newPrijava.prijavaName);
    this.router.navigate(['/prijava/new'], { state: { prijava: newPrijava } });
  }

  previousState(): void {
    window.history.back();
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }
}
