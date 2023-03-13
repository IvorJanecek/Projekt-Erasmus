import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { isPresent } from 'app/core/util/operators';
import { Observable } from 'rxjs';
import { NewZahtjev, IZahtjev } from '../zatjev.model';

export type PartialUpdateZahtjev = Partial<IZahtjev> & Pick<IZahtjev, 'id'>;

export type EntityResponseType = HttpResponse<IZahtjev>;
export type EntityArrayResponseType = HttpResponse<IZahtjev[]>;

@Injectable({
  providedIn: 'root',
})
export class ZahtjevService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/zahtjevs');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(zahtjev: NewZahtjev): Observable<EntityResponseType> {
    return this.http.post<IZahtjev>(this.resourceUrl, zahtjev, { observe: 'response' });
  }

  update(zahtjev: IZahtjev): Observable<EntityResponseType> {
    return this.http.put<IZahtjev>(`${this.resourceUrl}/${this.getZahtjevIdentifier(zahtjev)}`, zahtjev, { observe: 'response' });
  }

  partialUpdate(zahtjev: PartialUpdateZahtjev): Observable<EntityResponseType> {
    return this.http.patch<IZahtjev>(`${this.resourceUrl}/${this.getZahtjevIdentifier(zahtjev)}`, zahtjev, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IZahtjev>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IZahtjev[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getZahtjeviByNatjecaj(natjecajId: number): Observable<IZahtjev[]> {
    const url = `${this.resourceUrl}/natjecaj/${natjecajId}`;
    return this.http.get<IZahtjev[]>(url);
  }

  getZahtjevIdentifier(zahtjev: Pick<IZahtjev, 'id'>): number {
    return zahtjev.id;
  }

  compareZahtjev(o1: Pick<IZahtjev, 'id'> | null, o2: Pick<IZahtjev, 'id'> | null): boolean {
    return o1 && o2 ? this.getZahtjevIdentifier(o1) === this.getZahtjevIdentifier(o2) : o1 === o2;
  }

  addZahtjevToCollectionIfMissing<Type extends Pick<IZahtjev, 'id'>>(
    zahtjevCollection: Type[],
    ...zahtjevsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const zahtjevs: Type[] = zahtjevsToCheck.filter(isPresent);
    if (zahtjevs.length > 0) {
      const zahtjevCollectionIdentifiers = zahtjevCollection.map(zahtjevItem => this.getZahtjevIdentifier(zahtjevItem)!);
      const zahtjevsToAdd = zahtjevs.filter(zahtjevItem => {
        const zahtjevIdentifier = this.getZahtjevIdentifier(zahtjevItem);
        if (zahtjevCollectionIdentifiers.includes(zahtjevIdentifier)) {
          return false;
        }
        zahtjevCollectionIdentifiers.push(zahtjevIdentifier);
        return true;
      });
      return [...zahtjevsToAdd, ...zahtjevCollection];
    }
    return zahtjevCollection;
  }
}
