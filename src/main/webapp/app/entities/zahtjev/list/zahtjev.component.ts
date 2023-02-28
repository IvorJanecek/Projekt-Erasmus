import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, ParamMap, Data } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ITEM_DELETED_EVENT, SORT, DEFAULT_SORT_DATA, ASC, DESC } from 'app/config/navigation.constants';
import { SortService } from 'app/shared/sort/sort.service';
import { filter, switchMap, Observable, combineLatest, tap } from 'rxjs';
import { ZahtjevDeleteDialogComponent } from '../delete/zahtjev-delete-dialog.component';
import { ZahtjevService, EntityArrayResponseType } from '../service/zahtjev.service';
import { IZahtjev } from '../zatjev.model';

@Component({
  selector: 'jhi-zahtjev',
  templateUrl: './zahtjev.component.html',
})
export class ZahtjevComponent implements OnInit {
  zahtjevs?: IZahtjev[];
  isLoading = false;

  predicate = 'id';
  ascending = true;

  constructor(
    protected zahtjevService: ZahtjevService,
    protected activatedRoute: ActivatedRoute,
    public router: Router,
    protected sortService: SortService,
    protected modalService: NgbModal
  ) {}

  trackId = (_index: number, item: IZahtjev): number => this.zahtjevService.getZahtjevIdentifier(item);

  ngOnInit(): void {
    this.load();
  }

  delete(zahtjev: IZahtjev): void {
    const modalRef = this.modalService.open(ZahtjevDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.zahtjev = zahtjev;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed
      .pipe(
        filter(reason => reason === ITEM_DELETED_EVENT),
        switchMap(() => this.loadFromBackendWithRouteInformations())
      )
      .subscribe({
        next: (res: EntityArrayResponseType) => {
          this.onResponseSuccess(res);
        },
      });
  }

  load(): void {
    this.loadFromBackendWithRouteInformations().subscribe({
      next: (res: EntityArrayResponseType) => {
        this.onResponseSuccess(res);
      },
    });
  }

  navigateToWithComponentValues(): void {
    this.handleNavigation(this.predicate, this.ascending);
  }

  protected loadFromBackendWithRouteInformations(): Observable<EntityArrayResponseType> {
    return combineLatest([this.activatedRoute.queryParamMap, this.activatedRoute.data]).pipe(
      tap(([params, data]) => this.fillComponentAttributeFromRoute(params, data)),
      switchMap(() => this.queryBackend(this.predicate, this.ascending))
    );
  }

  protected fillComponentAttributeFromRoute(params: ParamMap, data: Data): void {
    const sort = (params.get(SORT) ?? data[DEFAULT_SORT_DATA]).split(',');
    this.predicate = sort[0];
    this.ascending = sort[1] === ASC;
  }

  protected onResponseSuccess(response: EntityArrayResponseType): void {
    const dataFromBody = this.fillComponentAttributesFromResponseBody(response.body);
    this.zahtjevs = this.refineData(dataFromBody);
  }

  protected refineData(data: IZahtjev[]): IZahtjev[] {
    return data.sort(this.sortService.startSort(this.predicate, this.ascending ? 1 : -1));
  }

  protected fillComponentAttributesFromResponseBody(data: IZahtjev[] | null): IZahtjev[] {
    return data ?? [];
  }

  protected queryBackend(predicate?: string, ascending?: boolean): Observable<EntityArrayResponseType> {
    this.isLoading = true;
    const queryObject = {
      sort: this.getSortQueryParam(predicate, ascending),
    };
    return this.zahtjevService.query(queryObject).pipe(tap(() => (this.isLoading = false)));
  }

  protected handleNavigation(predicate?: string, ascending?: boolean): void {
    const queryParamsObj = {
      sort: this.getSortQueryParam(predicate, ascending),
    };

    this.router.navigate(['./'], {
      relativeTo: this.activatedRoute,
      queryParams: queryParamsObj,
    });
  }

  protected getSortQueryParam(predicate = this.predicate, ascending = this.ascending): string[] {
    const ascendingQueryParam = ascending ? ASC : DESC;
    if (predicate === '') {
      return [];
    } else {
      return [predicate + ',' + ascendingQueryParam];
    }
  }
}
