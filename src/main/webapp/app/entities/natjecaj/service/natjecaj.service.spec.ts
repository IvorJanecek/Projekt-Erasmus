import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { INatjecaj } from '../natjecaj.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../natjecaj.test-samples';

import { NatjecajService, RestNatjecaj } from './natjecaj.service';

const requireRestSample: RestNatjecaj = {
  ...sampleWithRequiredData,
  createDate: sampleWithRequiredData.createDate?.toJSON(),
  datumOd: sampleWithRequiredData.datumOd?.format(DATE_FORMAT),
  datumDo: sampleWithRequiredData.datumDo?.format(DATE_FORMAT),
};

describe('Natjecaj Service', () => {
  let service: NatjecajService;
  let httpMock: HttpTestingController;
  let expectedResult: INatjecaj | INatjecaj[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(NatjecajService);
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

    it('should create a Natjecaj', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const natjecaj = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(natjecaj).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Natjecaj', () => {
      const natjecaj = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(natjecaj).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Natjecaj', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Natjecaj', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Natjecaj', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addNatjecajToCollectionIfMissing', () => {
      it('should add a Natjecaj to an empty array', () => {
        const natjecaj: INatjecaj = sampleWithRequiredData;
        expectedResult = service.addNatjecajToCollectionIfMissing([], natjecaj);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(natjecaj);
      });

      it('should not add a Natjecaj to an array that contains it', () => {
        const natjecaj: INatjecaj = sampleWithRequiredData;
        const natjecajCollection: INatjecaj[] = [
          {
            ...natjecaj,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addNatjecajToCollectionIfMissing(natjecajCollection, natjecaj);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Natjecaj to an array that doesn't contain it", () => {
        const natjecaj: INatjecaj = sampleWithRequiredData;
        const natjecajCollection: INatjecaj[] = [sampleWithPartialData];
        expectedResult = service.addNatjecajToCollectionIfMissing(natjecajCollection, natjecaj);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(natjecaj);
      });

      it('should add only unique Natjecaj to an array', () => {
        const natjecajArray: INatjecaj[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const natjecajCollection: INatjecaj[] = [sampleWithRequiredData];
        expectedResult = service.addNatjecajToCollectionIfMissing(natjecajCollection, ...natjecajArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const natjecaj: INatjecaj = sampleWithRequiredData;
        const natjecaj2: INatjecaj = sampleWithPartialData;
        expectedResult = service.addNatjecajToCollectionIfMissing([], natjecaj, natjecaj2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(natjecaj);
        expect(expectedResult).toContain(natjecaj2);
      });

      it('should accept null and undefined values', () => {
        const natjecaj: INatjecaj = sampleWithRequiredData;
        expectedResult = service.addNatjecajToCollectionIfMissing([], null, natjecaj, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(natjecaj);
      });

      it('should return initial array if no Natjecaj is added', () => {
        const natjecajCollection: INatjecaj[] = [sampleWithRequiredData];
        expectedResult = service.addNatjecajToCollectionIfMissing(natjecajCollection, undefined, null);
        expect(expectedResult).toEqual(natjecajCollection);
      });
    });

    describe('compareNatjecaj', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareNatjecaj(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareNatjecaj(entity1, entity2);
        const compareResult2 = service.compareNatjecaj(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareNatjecaj(entity1, entity2);
        const compareResult2 = service.compareNatjecaj(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareNatjecaj(entity1, entity2);
        const compareResult2 = service.compareNatjecaj(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
