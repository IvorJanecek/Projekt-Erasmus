import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { NatjecajFormService, NatjecajFormGroup } from './natjecaj-form.service';
import { INatjecaj } from '../natjecaj.model';
import { NatjecajService } from '../service/natjecaj.service';
import { Status } from 'app/entities/enumerations/status.model';
import { Korisnik } from 'app/entities/enumerations/korisnik.model';

@Component({
  selector: 'jhi-natjecaj-update',
  templateUrl: './natjecaj-update.component.html',
})
export class NatjecajUpdateComponent implements OnInit {
  isSaving = false;
  natjecaj: INatjecaj | null = null;
  statusValues = Object.keys(Status);
  korisnikValues = Object.keys(Korisnik);

  editForm: NatjecajFormGroup = this.natjecajFormService.createNatjecajFormGroup();

  constructor(
    protected natjecajService: NatjecajService,
    protected natjecajFormService: NatjecajFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ natjecaj }) => {
      this.natjecaj = natjecaj;
      if (natjecaj) {
        this.updateForm(natjecaj);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const natjecaj = this.natjecajFormService.getNatjecaj(this.editForm);
    if (natjecaj.id !== null) {
      this.subscribeToSaveResponse(this.natjecajService.update(natjecaj));
    } else {
      this.subscribeToSaveResponse(this.natjecajService.create(natjecaj));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INatjecaj>>): void {
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

  protected updateForm(natjecaj: INatjecaj): void {
    this.natjecaj = natjecaj;
    this.natjecajFormService.resetForm(this.editForm, natjecaj);
  }
}
