import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { INatjecaj, NewNatjecaj } from '../natjecaj.model';

export type PartialUpdateNatjecaj = Partial<INatjecaj> & Pick<INatjecaj, 'id'>;

type RestOf<T extends INatjecaj | NewNatjecaj> = Omit<T, 'createDate' | 'datumOd' | 'datumDo'> & {
  createDate?: string | null;
  datumOd?: string | null;
  datumDo?: string | null;
};

export type RestNatjecaj = RestOf<INatjecaj>;

export type NewRestNatjecaj = RestOf<NewNatjecaj>;

export type PartialUpdateRestNatjecaj = RestOf<PartialUpdateNatjecaj>;

export type EntityResponseType = HttpResponse<INatjecaj>;
export type EntityArrayResponseType = HttpResponse<INatjecaj[]>;

@Injectable({ providedIn: 'root' })
export class NatjecajService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/natjecajs');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(natjecaj: NewNatjecaj): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(natjecaj);
    return this.http
      .post<RestNatjecaj>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(natjecaj: INatjecaj): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(natjecaj);
    return this.http
      .put<RestNatjecaj>(`${this.resourceUrl}/${this.getNatjecajIdentifier(natjecaj)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(natjecaj: PartialUpdateNatjecaj): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(natjecaj);
    return this.http
      .patch<RestNatjecaj>(`${this.resourceUrl}/${this.getNatjecajIdentifier(natjecaj)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestNatjecaj>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestNatjecaj[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getNatjecajIdentifier(natjecaj: Pick<INatjecaj, 'id'>): number {
    return natjecaj.id;
  }

  compareNatjecaj(o1: Pick<INatjecaj, 'id'> | null, o2: Pick<INatjecaj, 'id'> | null): boolean {
    return o1 && o2 ? this.getNatjecajIdentifier(o1) === this.getNatjecajIdentifier(o2) : o1 === o2;
  }

  addNatjecajToCollectionIfMissing<Type extends Pick<INatjecaj, 'id'>>(
    natjecajCollection: Type[],
    ...natjecajsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const natjecajs: Type[] = natjecajsToCheck.filter(isPresent);
    if (natjecajs.length > 0) {
      const natjecajCollectionIdentifiers = natjecajCollection.map(natjecajItem => this.getNatjecajIdentifier(natjecajItem)!);
      const natjecajsToAdd = natjecajs.filter(natjecajItem => {
        const natjecajIdentifier = this.getNatjecajIdentifier(natjecajItem);
        if (natjecajCollectionIdentifiers.includes(natjecajIdentifier)) {
          return false;
        }
        natjecajCollectionIdentifiers.push(natjecajIdentifier);
        return true;
      });
      return [...natjecajsToAdd, ...natjecajCollection];
    }
    return natjecajCollection;
  }

  protected convertDateFromClient<T extends INatjecaj | NewNatjecaj | PartialUpdateNatjecaj>(natjecaj: T): RestOf<T> {
    return {
      ...natjecaj,
      createDate: natjecaj.createDate?.toJSON() ?? null,
      datumOd: natjecaj.datumOd?.format(DATE_FORMAT) ?? null,
      datumDo: natjecaj.datumDo?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restNatjecaj: RestNatjecaj): INatjecaj {
    return {
      ...restNatjecaj,
      createDate: restNatjecaj.createDate ? dayjs(restNatjecaj.createDate) : undefined,
      datumOd: restNatjecaj.datumOd ? dayjs(restNatjecaj.datumOd) : undefined,
      datumDo: restNatjecaj.datumDo ? dayjs(restNatjecaj.datumDo) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestNatjecaj>): HttpResponse<INatjecaj> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestNatjecaj[]>): HttpResponse<INatjecaj[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
