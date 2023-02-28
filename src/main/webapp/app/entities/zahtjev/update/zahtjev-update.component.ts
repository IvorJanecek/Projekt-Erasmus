import { HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { INatjecaj } from 'app/entities/natjecaj/natjecaj.model';
import { NatjecajService } from 'app/entities/natjecaj/service/natjecaj.service';
import { Observable, finalize, map } from 'rxjs';
import { ZahtjevService } from '../service/zahtjev.service';
import { IZahtjev } from '../zatjev.model';
import { ZahtjevFormGroup, ZahtjevFormService } from './zahtjev-form.service';

@Component({
  selector: 'jhi-zahtjev-update',
  templateUrl: './zahtjev-update.component.html',
})
export class ZahtjevUpdateComponent implements OnInit {
  isSaving = false;
  zahtjev: IZahtjev | null = null;

  natjecajsSharedCollection: INatjecaj[] = [];

  editForm: ZahtjevFormGroup = this.zahtjevFormService.createZahtjevFormGroup();

  constructor(
    protected zahtjevService: ZahtjevService,
    protected zahtjevFormService: ZahtjevFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ zahtjev }) => {
      this.zahtjev = zahtjev;
      if (zahtjev) {
        this.updateForm(zahtjev);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const zahtjev = this.zahtjevFormService.getZahtjev(this.editForm);
    if (zahtjev.id !== null) {
      this.subscribeToSaveResponse(this.zahtjevService.update(zahtjev));
    } else {
      this.subscribeToSaveResponse(this.zahtjevService.create(zahtjev));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IZahtjev>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
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

  protected updateForm(zahtjev: IZahtjev): void {
    this.zahtjev = zahtjev;
    this.zahtjevFormService.resetForm(this.editForm, zahtjev);
  }
}
