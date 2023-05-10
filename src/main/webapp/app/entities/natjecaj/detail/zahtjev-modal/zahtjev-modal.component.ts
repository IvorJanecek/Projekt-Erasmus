import { HttpResponse } from '@angular/common/http';
import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ZahtjevService } from 'app/entities/zahtjev/service/zahtjev.service';
import { ZahtjevFormGroup, ZahtjevFormService } from 'app/entities/zahtjev/update/zahtjev-form.service';
import { IZahtjev } from 'app/entities/zahtjev/zatjev.model';
import { finalize, Observable } from 'rxjs';
import { INatjecaj } from '../../natjecaj.model';
import { NatjecajService } from '../../service/natjecaj.service';

@Component({
  selector: 'jhi-zahtjev-modal',
  templateUrl: './zahtjev-modal.component.html',
  styleUrls: ['./zahtjev-modal.component.scss'],
})
export class ZahtjevModalComponent implements OnInit {
  numberOfEntities: number | null = null;
  numZahtjevsToCreate = 1;
  isSaving = false;
  editModal = false;
  zahtjev: IZahtjev | null = null;

  natjecajsSharedCollection: INatjecaj[] = [];

  editForm: ZahtjevFormGroup = this.zahtjevFormService.createZahtjevFormGroup();

  constructor(
    private zahtjevService: ZahtjevService,
    protected zahtjevFormService: ZahtjevFormService,
    public modal: NgbActiveModal,
    protected natjecajService: NatjecajService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.zahtjevFormService.resetForm(this.editForm, history.state.zahtjev);
  }

  @Input() natjecaj: INatjecaj | undefined;

  onCreateZahtjevsSubmit() {
    for (let i = 0; i < this.numZahtjevsToCreate; i++) {
      this.isSaving = true;
      const zahtjev = this.zahtjevFormService.getZahtjev(this.editForm);
      zahtjev.natjecaj = this.natjecaj; // set the same natjecaj value for all Zahtjevs
      // set any other properties for the Zahtjev entity here
      // staro   this.zahtjevService.create(zahtjev).subscribe(() => {
      // handle successful creation of the Zahtjev entity
      // staro   });

      if (zahtjev.id == null) {
        this.subscribeToSaveResponse(this.zahtjevService.create(zahtjev));
      }
    }
    this.modal.dismiss();
  }

  compareNatjecaj = (o1: INatjecaj | null, o2: INatjecaj | null): boolean => this.natjecajService.compareNatjecaj(o1, o2);

  save(): void {
    this.isSaving = true;
    let zahtjev = this.zahtjevFormService.getZahtjev(this.editForm);
    zahtjev.natjecaj = this.natjecaj;
    if (this.editModal) {
      zahtjev.id = this.zahtjev!.id;
    }
    if (zahtjev.id !== null) {
      this.subscribeToSaveResponse(this.zahtjevService.update(zahtjev));
    } else {
      this.subscribeToSaveResponse(this.zahtjevService.create(zahtjev));
    }
    console.log('**Ovdje LOGIRAMO!!! **');
    console.log(zahtjev);
    console.log(this.natjecaj);
    this.modal.dismiss();
    console.log('**PRESTAJE LOGGING**');
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IZahtjev>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
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

  previousState(): void {
    window.history.back();
  }
}
