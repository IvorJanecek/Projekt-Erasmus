import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { MobilnostFormService, MobilnostFormGroup } from './mobilnost-form.service';
import { IMobilnost } from '../mobilnost.model';
import { MobilnostService } from '../service/mobilnost.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { INatjecaj } from 'app/entities/natjecaj/natjecaj.model';
import { NatjecajService } from 'app/entities/natjecaj/service/natjecaj.service';
import { IPrijava } from 'app/entities/prijava/prijava.model';
import { PrijavaService } from 'app/entities/prijava/service/prijava.service';

@Component({
  selector: 'jhi-mobilnost-update',
  templateUrl: './mobilnost-update.component.html',
})
export class MobilnostUpdateComponent implements OnInit {
  isSaving = false;
  mobilnost: IMobilnost | null = null;

  natjecajsCollection: INatjecaj[] = [];
  prijavasCollection: IPrijava[] = [];

  editForm: MobilnostFormGroup = this.mobilnostFormService.createMobilnostFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected mobilnostService: MobilnostService,
    protected mobilnostFormService: MobilnostFormService,
    protected natjecajService: NatjecajService,
    protected prijavaService: PrijavaService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareNatjecaj = (o1: INatjecaj | null, o2: INatjecaj | null): boolean => this.natjecajService.compareNatjecaj(o1, o2);

  comparePrijava = (o1: IPrijava | null, o2: IPrijava | null): boolean => this.prijavaService.comparePrijava(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ mobilnost }) => {
      this.mobilnost = mobilnost;
      if (mobilnost) {
        this.updateForm(mobilnost);
      }

      this.loadRelationshipsOptions();
    });
    this.mobilnost = history.state.mobilnost;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(new EventWithContent<AlertError>('erasmusApp.error', { message: err.message })),
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const mobilnost = this.mobilnostFormService.getMobilnost(this.editForm);
    if (mobilnost.id !== null) {
      this.subscribeToSaveResponse(this.mobilnostService.update(mobilnost));
    } else {
      this.subscribeToSaveResponse(this.mobilnostService.create(mobilnost));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMobilnost>>): void {
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

  protected updateForm(mobilnost: IMobilnost): void {
    this.mobilnost = mobilnost;
    this.mobilnostFormService.resetForm(this.editForm, mobilnost);

    this.natjecajsCollection = this.natjecajService.addNatjecajToCollectionIfMissing<INatjecaj>(
      this.natjecajsCollection,
      mobilnost.natjecaj
    );
    this.prijavasCollection = this.prijavaService.addPrijavaToCollectionIfMissing<IPrijava>(this.prijavasCollection, mobilnost.prijava);
  }

  protected loadRelationshipsOptions(): void {
    this.prijavaService
      .query({ filter: 'mobilnost-is-null' })
      .pipe(map((res: HttpResponse<IPrijava[]>) => res.body ?? []))
      .pipe(map((prijavas: IPrijava[]) => this.prijavaService.addPrijavaToCollectionIfMissing<IPrijava>(prijavas, this.mobilnost?.prijava)))
      .subscribe((prijavas: IPrijava[]) => (this.prijavasCollection = prijavas));
  }
}
