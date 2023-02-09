import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IMobilnost, NewMobilnost } from '../mobilnost.model';

export type PartialUpdateMobilnost = Partial<IMobilnost> & Pick<IMobilnost, 'id'>;

type RestOf<T extends IMobilnost | NewMobilnost> = Omit<T, 'createdDate'> & {
  createdDate?: string | null;
};

export type RestMobilnost = RestOf<IMobilnost>;

export type NewRestMobilnost = RestOf<NewMobilnost>;

export type PartialUpdateRestMobilnost = RestOf<PartialUpdateMobilnost>;

export type EntityResponseType = HttpResponse<IMobilnost>;
export type EntityArrayResponseType = HttpResponse<IMobilnost[]>;

@Injectable({ providedIn: 'root' })
export class MobilnostService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/mobilnosts');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(mobilnost: NewMobilnost): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(mobilnost);
    return this.http
      .post<RestMobilnost>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(mobilnost: IMobilnost): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(mobilnost);
    return this.http
      .put<RestMobilnost>(`${this.resourceUrl}/${this.getMobilnostIdentifier(mobilnost)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(mobilnost: PartialUpdateMobilnost): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(mobilnost);
    return this.http
      .patch<RestMobilnost>(`${this.resourceUrl}/${this.getMobilnostIdentifier(mobilnost)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestMobilnost>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestMobilnost[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getMobilnostIdentifier(mobilnost: Pick<IMobilnost, 'id'>): number {
    return mobilnost.id;
  }

  compareMobilnost(o1: Pick<IMobilnost, 'id'> | null, o2: Pick<IMobilnost, 'id'> | null): boolean {
    return o1 && o2 ? this.getMobilnostIdentifier(o1) === this.getMobilnostIdentifier(o2) : o1 === o2;
  }

  addMobilnostToCollectionIfMissing<Type extends Pick<IMobilnost, 'id'>>(
    mobilnostCollection: Type[],
    ...mobilnostsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const mobilnosts: Type[] = mobilnostsToCheck.filter(isPresent);
    if (mobilnosts.length > 0) {
      const mobilnostCollectionIdentifiers = mobilnostCollection.map(mobilnostItem => this.getMobilnostIdentifier(mobilnostItem)!);
      const mobilnostsToAdd = mobilnosts.filter(mobilnostItem => {
        const mobilnostIdentifier = this.getMobilnostIdentifier(mobilnostItem);
        if (mobilnostCollectionIdentifiers.includes(mobilnostIdentifier)) {
          return false;
        }
        mobilnostCollectionIdentifiers.push(mobilnostIdentifier);
        return true;
      });
      return [...mobilnostsToAdd, ...mobilnostCollection];
    }
    return mobilnostCollection;
  }

  protected convertDateFromClient<T extends IMobilnost | NewMobilnost | PartialUpdateMobilnost>(mobilnost: T): RestOf<T> {
    return {
      ...mobilnost,
      createdDate: mobilnost.createdDate?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restMobilnost: RestMobilnost): IMobilnost {
    return {
      ...restMobilnost,
      createdDate: restMobilnost.createdDate ? dayjs(restMobilnost.createdDate) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestMobilnost>): HttpResponse<IMobilnost> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestMobilnost[]>): HttpResponse<IMobilnost[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
