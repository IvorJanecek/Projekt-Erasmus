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
    private zahtjevService: ZahtjevService
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

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  previousState(): void {
    window.history.back();
  }
  createMobilnost(prijava: Pick<IPrijava, 'id' | 'natjecaj' | 'prihvacen'>): void {
    // set the prihvacen field to true for the Prijava entity
    prijava.prihvacen = true;
    console.log(prijava.prihvacen);

    const PartialUpdatePrijava = {
      id: prijava.id,
      prihvacen: prijava.prihvacen,
    };

    this.prijavaService.partialUpdate(PartialUpdatePrijava).subscribe(() => {
      console.log(prijava.prihvacen);
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

  createNewMobilnost(prijava: Pick<IPrijava, 'id' | 'natjecaj' | 'prihvacen' | 'user'>): void {
    // set the prihvacen field to true for the Prijava entity
    prijava.prihvacen = true;
    console.log(prijava.prihvacen);

    const PartialUpdatePrijava = {
      id: prijava.id,
      prihvacen: prijava.prihvacen,
    };

    this.prijavaService.partialUpdate(PartialUpdatePrijava).subscribe(() => {
      console.log(prijava.prihvacen);
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
