import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map, tap } from 'rxjs/operators';

import { PrijavaFormService, PrijavaFormGroup } from './prijava-form.service';
import { IPrijava } from '../prijava.model';
import { PrijavaService } from '../service/prijava.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { IFakultet } from 'app/entities/fakultet/fakultet.model';
import { FakultetService } from 'app/entities/fakultet/service/fakultet.service';
import { INatjecaj } from 'app/entities/natjecaj/natjecaj.model';
import { NatjecajService } from 'app/entities/natjecaj/service/natjecaj.service';
import { Kategorija } from 'app/entities/enumerations/kategorija.model';
import { Account } from 'app/core/auth/account.model';
import { AccountService } from 'app/core/auth/account.service';
import { StatusPrijave } from 'app/entities/enumerations/statusprijave.mode';

@Component({
  selector: 'jhi-prijava-update',
  templateUrl: './prijava-update.component.html',
})
export class PrijavaUpdateComponent implements OnInit {
  isSaving = false;
  prijava: IPrijava | null = null;
  kategorijaValues = Object.keys(Kategorija);
  statusPrijaveValues = Object.keys(StatusPrijave);

  usersSharedCollection: IUser[] = [];
  fakultetsSharedCollection: IFakultet[] = [];
  natjecajsSharedCollection: INatjecaj[] = [];

  editForm: PrijavaFormGroup = this.prijavaFormService.createPrijavaFormGroup();
  account$?: Observable<Account | null>;
  currentAccount: Account | null = null;
  currentUser: IUser | null = null;

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected prijavaService: PrijavaService,
    protected prijavaFormService: PrijavaFormService,
    protected userService: UserService,
    protected fakultetService: FakultetService,
    protected natjecajService: NatjecajService,
    protected activatedRoute: ActivatedRoute,
    protected accountService: AccountService,
    private router: Router
  ) {}

  compareUser = (o1: IUser | null, o2: IUser | null): boolean => this.userService.compareUser(o1, o2);

  compareFakultet = (o1: IFakultet | null, o2: IFakultet | null): boolean => this.fakultetService.compareFakultet(o1, o2);

  compareNatjecaj = (o1: INatjecaj | null, o2: INatjecaj | null): boolean => this.natjecajService.compareNatjecaj(o1, o2);

  ngOnInit(): void {
    this.account$ = this.accountService.identity();
    this.account$?.subscribe(result => {
      this.currentAccount = result;
    });

    this.activatedRoute.data.subscribe(({ prijava }) => {
      this.prijava = prijava;
      if (prijava) {
        this.updateForm(prijava);
      } else {
        this.editForm.patchValue({ statusPrijave: StatusPrijave.NOVA_PRIJAVA });
      }

      this.loadRelationshipsOptions();
    });

    this.prijava = history.state.prijava;
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
    let prijava = this.prijavaFormService.getPrijava(this.editForm);
    prijava.prijavaName = 'Prijava za ' + String(this.prijava?.natjecaj?.name);
    prijava.natjecaj = this.prijava?.natjecaj;
    console.log(prijava.prijavaName);
    console.log(prijava.natjecaj);

    for (let i = 0; i < this.usersSharedCollection.length; i++) {
      if (this.usersSharedCollection[i].login === this.currentAccount?.login) {
        this.currentUser = this.usersSharedCollection[i];
      }
    }

    prijava.user = this.currentUser;

    if (prijava.id !== null) {
      this.subscribeToSaveResponse(this.prijavaService.update(prijava));
    } else {
      this.subscribeToSaveResponse(this.prijavaService.create(prijava));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPrijava>>): void {
    result
      .pipe(
        finalize(() => this.onSaveFinalize()),
        tap(res => {
          const prijavaId = res.body?.id;
          if (prijavaId) {
            const route = `/prijava/upload-files/${prijavaId}`;
            this.router.navigate([route], { state: { natjecaj: this.prijava?.natjecaj } });
          }
        })
      )
      .subscribe({
        next: () => this.onSaveSuccess(),
        error: () => this.onSaveError(),
      });
  }

  protected onSaveSuccess(): void {}

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(prijava: IPrijava): void {
    this.prijava = prijava;
    this.prijavaFormService.resetForm(this.editForm, prijava);

    this.usersSharedCollection = this.userService.addUserToCollectionIfMissing<IUser>(this.usersSharedCollection, prijava.user);
    this.fakultetsSharedCollection = this.fakultetService.addFakultetToCollectionIfMissing<IFakultet>(
      this.fakultetsSharedCollection,
      prijava.fakultet
    );
  }

  protected loadRelationshipsOptions(): void {
    this.userService
      .query()
      .pipe(map((res: HttpResponse<IUser[]>) => res.body ?? []))
      .pipe(map((users: IUser[]) => this.userService.addUserToCollectionIfMissing<IUser>(users, this.prijava?.user)))
      .subscribe((users: IUser[]) => (this.usersSharedCollection = users));

    this.fakultetService
      .query()
      .pipe(map((res: HttpResponse<IFakultet[]>) => res.body ?? []))
      .pipe(
        map((fakultets: IFakultet[]) => this.fakultetService.addFakultetToCollectionIfMissing<IFakultet>(fakultets, this.prijava?.fakultet))
      )
      .subscribe((fakultets: IFakultet[]) => (this.fakultetsSharedCollection = fakultets));
  }
}
