import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IPrijava } from '../prijava.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../prijava.test-samples';

import { PrijavaService, RestPrijava } from './prijava.service';

const requireRestSample: RestPrijava = {
  ...sampleWithRequiredData,
  createdDate: sampleWithRequiredData.createdDate?.toJSON(),
  trajanjeOd: sampleWithRequiredData.trajanjeOd?.format(DATE_FORMAT),
  trajanjeDo: sampleWithRequiredData.trajanjeDo?.format(DATE_FORMAT),
};

describe('Prijava Service', () => {
  let service: PrijavaService;
  let httpMock: HttpTestingController;
  let expectedResult: IPrijava | IPrijava[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PrijavaService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a Prijava', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const prijava = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(prijava).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Prijava', () => {
      const prijava = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(prijava).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Prijava', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Prijava', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Prijava', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addPrijavaToCollectionIfMissing', () => {
      it('should add a Prijava to an empty array', () => {
        const prijava: IPrijava = sampleWithRequiredData;
        expectedResult = service.addPrijavaToCollectionIfMissing([], prijava);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(prijava);
      });

      it('should not add a Prijava to an array that contains it', () => {
        const prijava: IPrijava = sampleWithRequiredData;
        const prijavaCollection: IPrijava[] = [
          {
            ...prijava,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addPrijavaToCollectionIfMissing(prijavaCollection, prijava);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Prijava to an array that doesn't contain it", () => {
        const prijava: IPrijava = sampleWithRequiredData;
        const prijavaCollection: IPrijava[] = [sampleWithPartialData];
        expectedResult = service.addPrijavaToCollectionIfMissing(prijavaCollection, prijava);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(prijava);
      });

      it('should add only unique Prijava to an array', () => {
        const prijavaArray: IPrijava[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const prijavaCollection: IPrijava[] = [sampleWithRequiredData];
        expectedResult = service.addPrijavaToCollectionIfMissing(prijavaCollection, ...prijavaArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const prijava: IPrijava = sampleWithRequiredData;
        const prijava2: IPrijava = sampleWithPartialData;
        expectedResult = service.addPrijavaToCollectionIfMissing([], prijava, prijava2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(prijava);
        expect(expectedResult).toContain(prijava2);
      });

      it('should accept null and undefined values', () => {
        const prijava: IPrijava = sampleWithRequiredData;
        expectedResult = service.addPrijavaToCollectionIfMissing([], null, prijava, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(prijava);
      });

      it('should return initial array if no Prijava is added', () => {
        const prijavaCollection: IPrijava[] = [sampleWithRequiredData];
        expectedResult = service.addPrijavaToCollectionIfMissing(prijavaCollection, undefined, null);
        expect(expectedResult).toEqual(prijavaCollection);
      });
    });

    describe('comparePrijava', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.comparePrijava(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.comparePrijava(entity1, entity2);
        const compareResult2 = service.comparePrijava(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.comparePrijava(entity1, entity2);
        const compareResult2 = service.comparePrijava(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.comparePrijava(entity1, entity2);
        const compareResult2 = service.comparePrijava(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
