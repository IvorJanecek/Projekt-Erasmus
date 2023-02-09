import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { FakultetFormService, FakultetFormGroup } from './fakultet-form.service';
import { IFakultet } from '../fakultet.model';
import { FakultetService } from '../service/fakultet.service';

@Component({
  selector: 'jhi-fakultet-update',
  templateUrl: './fakultet-update.component.html',
})
export class FakultetUpdateComponent implements OnInit {
  isSaving = false;
  fakultet: IFakultet | null = null;

  editForm: FakultetFormGroup = this.fakultetFormService.createFakultetFormGroup();

  constructor(
    protected fakultetService: FakultetService,
    protected fakultetFormService: FakultetFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fakultet }) => {
      this.fakultet = fakultet;
      if (fakultet) {
        this.updateForm(fakultet);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const fakultet = this.fakultetFormService.getFakultet(this.editForm);
    if (fakultet.id !== null) {
      this.subscribeToSaveResponse(this.fakultetService.update(fakultet));
    } else {
      this.subscribeToSaveResponse(this.fakultetService.create(fakultet));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFakultet>>): void {
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

  protected updateForm(fakultet: IFakultet): void {
    this.fakultet = fakultet;
    this.fakultetFormService.resetForm(this.editForm, fakultet);
  }
}
