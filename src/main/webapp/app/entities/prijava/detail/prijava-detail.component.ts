import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { IPrijava } from '../prijava.model';
import { DataUtils } from 'app/core/util/data-util.service';
import { IMobilnost } from 'app/entities/mobilnost/mobilnost.model';
import { finalize, Observable } from 'rxjs';
import { HttpResponse } from '@angular/common/http';
import { IUploadFile } from '../upload_files.model';
import { PrijavaService } from '../service/prijava.service';
import { IZahtjev } from 'app/entities/zahtjev/zatjev.model';
import { ZahtjevService } from 'app/entities/zahtjev/service/zahtjev.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ZahtjevModalComponent } from '../../natjecaj/detail/zahtjev-modal/zahtjev-modal.component';
import { FileModalComponent } from './file-modal/file-modal.component';
import { StatusPrijave } from 'app/entities/enumerations/statusprijave.mode';

@Component({
  selector: 'jhi-prijava-detail',
  templateUrl: './prijava-detail.component.html',
})
export class PrijavaDetailComponent implements OnInit {
  prijava: IPrijava | null = null;
  isSaving = false;
  mobilnostCollection: any;
  prijavaFormService: any;
  uploadFiles?: IUploadFile[];
  zahtjevs: IZahtjev[] | null = [];

  constructor(
    protected dataUtils: DataUtils,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    private prijavaService: PrijavaService,
    private zahtjevService: ZahtjevService,
    private modalService: NgbModal
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ prijava }) => {
      this.prijava = prijava;
    });
    if (this.prijava?.natjecaj?.id) {
      this.zahtjevService.findAllByNatjecajId(this.prijava.natjecaj.id).subscribe(result => {
        this.zahtjevs = result.body;
      });
    }
  }

  onModalHidden(): void {
    window.location.reload();
  }

  urediFile(file: Pick<IUploadFile, 'id'>): void {
    const modalRef = this.modalService.open(FileModalComponent, { centered: true });

    modalRef.componentInstance.uploadFile = file;

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

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  previousState(): void {
    window.history.back();
  }
  createMobilnost(prijava: Pick<IPrijava, 'id' | 'natjecaj' | 'statusPrijave'>): void {
    // Set the prijava.statusPrijave to PRIHVACEN for the Prijava entity
    prijava.statusPrijave = StatusPrijave.PRIHVACEN;
    console.log(prijava.statusPrijave);

    const partialUpdatePrijava = {
      id: prijava.id!,
      statusPrijave: prijava.statusPrijave || null,
    };

    this.prijavaService.partialUpdate(partialUpdatePrijava).subscribe(() => {
      console.log(prijava.statusPrijave);
      this.save();
    });
  }

  save(): void {
    this.isSaving = true;
    const prijava = this.prijavaFormService.getPrijava(this.editForm);
    if (prijava.id !== null) {
      this.subscribeToSaveResponse(this.prijavaService.update(prijava));
    } else {
      this.subscribeToSaveResponse(this.prijavaService.create(prijava));
    }
  }
  editForm(editForm: any) {
    throw new Error('Method not implemented.');
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPrijava>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  createNewMobilnost(prijava: Pick<IPrijava, 'id' | 'natjecaj' | 'statusPrijave' | 'user'>): void {
    // set the prihvacen field to true for the Prijava entity
    prijava.statusPrijave = StatusPrijave.PRIHVACEN;
    console.log(prijava.statusPrijave);

    const PartialUpdatePrijava = {
      id: prijava.id,
      statusPrijave: prijava.statusPrijave,
    };

    this.prijavaService.partialUpdate(PartialUpdatePrijava).subscribe(() => {
      console.log(prijava.statusPrijave);
      this.save();
    });

    const newMobilnost: IMobilnost = {
      prijava: prijava,
      id: prijava.id,
      natjecaj: prijava.natjecaj,
      user: prijava.user,
    };

    this.router.navigate(['/mobilnost/new'], { state: { mobilnost: newMobilnost } });
  }

  odbijPrijavu(prijava: Pick<IPrijava, 'id' | 'statusPrijave'>): void {
    // set the prihvacen field to true for the Prijava entity
    prijava.statusPrijave = StatusPrijave.ODBIJEN;
    console.log(prijava.statusPrijave);

    const PartialUpdatePrijava = {
      id: prijava.id,
      statusPrijave: prijava.statusPrijave,
    };

    this.prijavaService.partialUpdate(PartialUpdatePrijava).subscribe(() => {
      console.log(prijava.statusPrijave);
      this.save();
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }
}
