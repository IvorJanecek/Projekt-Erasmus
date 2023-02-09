import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IFakultet, NewFakultet } from '../fakultet.model';

export type PartialUpdateFakultet = Partial<IFakultet> & Pick<IFakultet, 'id'>;

export type EntityResponseType = HttpResponse<IFakultet>;
export type EntityArrayResponseType = HttpResponse<IFakultet[]>;

@Injectable({ providedIn: 'root' })
export class FakultetService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/fakultets');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(fakultet: NewFakultet): Observable<EntityResponseType> {
    return this.http.post<IFakultet>(this.resourceUrl, fakultet, { observe: 'response' });
  }

  update(fakultet: IFakultet): Observable<EntityResponseType> {
    return this.http.put<IFakultet>(`${this.resourceUrl}/${this.getFakultetIdentifier(fakultet)}`, fakultet, { observe: 'response' });
  }

  partialUpdate(fakultet: PartialUpdateFakultet): Observable<EntityResponseType> {
    return this.http.patch<IFakultet>(`${this.resourceUrl}/${this.getFakultetIdentifier(fakultet)}`, fakultet, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFakultet>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFakultet[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getFakultetIdentifier(fakultet: Pick<IFakultet, 'id'>): number {
    return fakultet.id;
  }

  compareFakultet(o1: Pick<IFakultet, 'id'> | null, o2: Pick<IFakultet, 'id'> | null): boolean {
    return o1 && o2 ? this.getFakultetIdentifier(o1) === this.getFakultetIdentifier(o2) : o1 === o2;
  }

  addFakultetToCollectionIfMissing<Type extends Pick<IFakultet, 'id'>>(
    fakultetCollection: Type[],
    ...fakultetsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const fakultets: Type[] = fakultetsToCheck.filter(isPresent);
    if (fakultets.length > 0) {
      const fakultetCollectionIdentifiers = fakultetCollection.map(fakultetItem => this.getFakultetIdentifier(fakultetItem)!);
      const fakultetsToAdd = fakultets.filter(fakultetItem => {
        const fakultetIdentifier = this.getFakultetIdentifier(fakultetItem);
        if (fakultetCollectionIdentifiers.includes(fakultetIdentifier)) {
          return false;
        }
        fakultetCollectionIdentifiers.push(fakultetIdentifier);
        return true;
      });
      return [...fakultetsToAdd, ...fakultetCollection];
    }
    return fakultetCollection;
  }
}
