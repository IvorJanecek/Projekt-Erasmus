import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPrijava, NewPrijava } from '../prijava.model';

export type PartialUpdatePrijava = Partial<IPrijava> & Pick<IPrijava, 'id'>;

type RestOf<T extends IPrijava | NewPrijava> = Omit<T, 'createdDate' | 'trajanjeOd' | 'trajanjeDo'> & {
  createdDate?: string | null;
  trajanjeOd?: string | null;
  trajanjeDo?: string | null;
};

export type RestPrijava = RestOf<IPrijava>;

export type NewRestPrijava = RestOf<NewPrijava>;

export type PartialUpdateRestPrijava = RestOf<PartialUpdatePrijava>;

export type EntityResponseType = HttpResponse<IPrijava>;
export type EntityArrayResponseType = HttpResponse<IPrijava[]>;

@Injectable({ providedIn: 'root' })
export class PrijavaService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/prijavas');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(prijava: NewPrijava): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(prijava);
    return this.http
      .post<RestPrijava>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(prijava: IPrijava): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(prijava);
    return this.http
      .put<RestPrijava>(`${this.resourceUrl}/${this.getPrijavaIdentifier(prijava)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(prijava: PartialUpdatePrijava): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(prijava);
    return this.http
      .patch<RestPrijava>(`${this.resourceUrl}/${this.getPrijavaIdentifier(prijava)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestPrijava>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestPrijava[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPrijavaIdentifier(prijava: Pick<IPrijava, 'id'>): number {
    return prijava.id;
  }

  comparePrijava(o1: Pick<IPrijava, 'id'> | null, o2: Pick<IPrijava, 'id'> | null): boolean {
    return o1 && o2 ? this.getPrijavaIdentifier(o1) === this.getPrijavaIdentifier(o2) : o1 === o2;
  }

  addPrijavaToCollectionIfMissing<Type extends Pick<IPrijava, 'id'>>(
    prijavaCollection: Type[],
    ...prijavasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const prijavas: Type[] = prijavasToCheck.filter(isPresent);
    if (prijavas.length > 0) {
      const prijavaCollectionIdentifiers = prijavaCollection.map(prijavaItem => this.getPrijavaIdentifier(prijavaItem)!);
      const prijavasToAdd = prijavas.filter(prijavaItem => {
        const prijavaIdentifier = this.getPrijavaIdentifier(prijavaItem);
        if (prijavaCollectionIdentifiers.includes(prijavaIdentifier)) {
          return false;
        }
        prijavaCollectionIdentifiers.push(prijavaIdentifier);
        return true;
      });
      return [...prijavasToAdd, ...prijavaCollection];
    }
    return prijavaCollection;
  }

  protected convertDateFromClient<T extends IPrijava | NewPrijava | PartialUpdatePrijava>(prijava: T): RestOf<T> {
    return {
      ...prijava,
      createdDate: prijava.createdDate?.toJSON() ?? null,
      trajanjeOd: prijava.trajanjeOd?.format(DATE_FORMAT) ?? null,
      trajanjeDo: prijava.trajanjeDo?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restPrijava: RestPrijava): IPrijava {
    return {
      ...restPrijava,
      createdDate: restPrijava.createdDate ? dayjs(restPrijava.createdDate) : undefined,
      trajanjeOd: restPrijava.trajanjeOd ? dayjs(restPrijava.trajanjeOd) : undefined,
      trajanjeDo: restPrijava.trajanjeDo ? dayjs(restPrijava.trajanjeDo) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestPrijava>): HttpResponse<IPrijava> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestPrijava[]>): HttpResponse<IPrijava[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
