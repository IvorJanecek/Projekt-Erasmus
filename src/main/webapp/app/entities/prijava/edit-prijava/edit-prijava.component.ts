import { HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { EventManager } from '@angular/platform-browser';
import { ActivatedRoute, Router } from '@angular/router';
import { IUser } from 'app/admin/user-management/user-management.model';
import { Account } from 'app/core/auth/account.model';
import { AccountService } from 'app/core/auth/account.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { EventWithContent } from 'app/core/util/event-manager.service';
import { Kategorija } from 'app/entities/enumerations/kategorija.model';
import { StatusPrijave } from 'app/entities/enumerations/statusprijave.mode';
import { IFakultet } from 'app/entities/fakultet/fakultet.model';
import { FakultetService } from 'app/entities/fakultet/service/fakultet.service';
import { INatjecaj } from 'app/entities/natjecaj/natjecaj.model';
import { NatjecajService } from 'app/entities/natjecaj/service/natjecaj.service';
import { UserService } from 'app/entities/user/user.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { Observable, finalize, tap, map } from 'rxjs';
import { IPrijava } from '../prijava.model';
import { PrijavaService } from '../service/prijava.service';
import { PrijavaFormGroup, PrijavaFormService } from '../update/prijava-form.service';

@Component({
  selector: 'jhi-edit-prijava',
  templateUrl: './edit-prijava.component.html',
  styleUrls: ['./edit-prijava.component.scss'],
})
export class EditPrijavaComponent implements OnInit {
  isSaving = false;
  prijava: IPrijava | null = null;
  kategorijaValues = Object.keys(Kategorija);

  usersSharedCollection: IUser[] = [];
  fakultetsSharedCollection: IFakultet[] = [];
  natjecajsSharedCollection: INatjecaj[] = [];

  editForm: PrijavaFormGroup = this.prijavaFormService.createPrijavaFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected prijavaService: PrijavaService,
    protected prijavaFormService: PrijavaFormService,
    protected userService: UserService,
    protected fakultetService: FakultetService,
    protected natjecajService: NatjecajService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareFakultet = (o1: IFakultet | null, o2: IFakultet | null): boolean => this.fakultetService.compareFakultet(o1, o2);

  compareNatjecaj = (o1: INatjecaj | null, o2: INatjecaj | null): boolean => this.natjecajService.compareNatjecaj(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ prijava }) => {
      this.prijava = prijava;
      if (prijava) {
        this.updateForm(prijava);
      }

      this.loadRelationshipsOptions();
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  previousState(): void {
    window.history.back();
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

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPrijava>>): void {
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

  protected updateForm(prijava: IPrijava): void {
    this.prijava = prijava;
    this.prijavaFormService.resetForm(this.editForm, prijava);

    this.fakultetsSharedCollection = this.fakultetService.addFakultetToCollectionIfMissing<IFakultet>(
      this.fakultetsSharedCollection,
      prijava.fakultet
    );
    this.natjecajsSharedCollection = this.natjecajService.addNatjecajToCollectionIfMissing<INatjecaj>(
      this.natjecajsSharedCollection,
      prijava.natjecaj
    );
  }

  protected loadRelationshipsOptions(): void {
    this.fakultetService
      .query()
      .pipe(map((res: HttpResponse<IFakultet[]>) => res.body ?? []))
      .pipe(
        map((fakultets: IFakultet[]) => this.fakultetService.addFakultetToCollectionIfMissing<IFakultet>(fakultets, this.prijava?.fakultet))
      )
      .subscribe((fakultets: IFakultet[]) => (this.fakultetsSharedCollection = fakultets));

    this.natjecajService
      .query()
      .pipe(map((res: HttpResponse<INatjecaj[]>) => res.body ?? []))
      .pipe(
        map((natjecajs: INatjecaj[]) => this.natjecajService.addNatjecajToCollectionIfMissing<INatjecaj>(natjecajs, this.prijava?.natjecaj))
      )
      .subscribe((natjecajs: INatjecaj[]) => (this.natjecajsSharedCollection = natjecajs));
  }
}
