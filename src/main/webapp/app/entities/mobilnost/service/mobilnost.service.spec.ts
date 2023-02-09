import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IMobilnost } from '../mobilnost.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../mobilnost.test-samples';

import { MobilnostService, RestMobilnost } from './mobilnost.service';

const requireRestSample: RestMobilnost = {
  ...sampleWithRequiredData,
  createdDate: sampleWithRequiredData.createdDate?.toJSON(),
};

describe('Mobilnost Service', () => {
  let service: MobilnostService;
  let httpMock: HttpTestingController;
  let expectedResult: IMobilnost | IMobilnost[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(MobilnostService);
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

    it('should create a Mobilnost', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const mobilnost = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(mobilnost).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Mobilnost', () => {
      const mobilnost = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(mobilnost).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Mobilnost', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Mobilnost', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Mobilnost', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addMobilnostToCollectionIfMissing', () => {
      it('should add a Mobilnost to an empty array', () => {
        const mobilnost: IMobilnost = sampleWithRequiredData;
        expectedResult = service.addMobilnostToCollectionIfMissing([], mobilnost);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(mobilnost);
      });

      it('should not add a Mobilnost to an array that contains it', () => {
        const mobilnost: IMobilnost = sampleWithRequiredData;
        const mobilnostCollection: IMobilnost[] = [
          {
            ...mobilnost,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addMobilnostToCollectionIfMissing(mobilnostCollection, mobilnost);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Mobilnost to an array that doesn't contain it", () => {
        const mobilnost: IMobilnost = sampleWithRequiredData;
        const mobilnostCollection: IMobilnost[] = [sampleWithPartialData];
        expectedResult = service.addMobilnostToCollectionIfMissing(mobilnostCollection, mobilnost);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(mobilnost);
      });

      it('should add only unique Mobilnost to an array', () => {
        const mobilnostArray: IMobilnost[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const mobilnostCollection: IMobilnost[] = [sampleWithRequiredData];
        expectedResult = service.addMobilnostToCollectionIfMissing(mobilnostCollection, ...mobilnostArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const mobilnost: IMobilnost = sampleWithRequiredData;
        const mobilnost2: IMobilnost = sampleWithPartialData;
        expectedResult = service.addMobilnostToCollectionIfMissing([], mobilnost, mobilnost2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(mobilnost);
        expect(expectedResult).toContain(mobilnost2);
      });

      it('should accept null and undefined values', () => {
        const mobilnost: IMobilnost = sampleWithRequiredData;
        expectedResult = service.addMobilnostToCollectionIfMissing([], null, mobilnost, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(mobilnost);
      });

      it('should return initial array if no Mobilnost is added', () => {
        const mobilnostCollection: IMobilnost[] = [sampleWithRequiredData];
        expectedResult = service.addMobilnostToCollectionIfMissing(mobilnostCollection, undefined, null);
        expect(expectedResult).toEqual(mobilnostCollection);
      });
    });

    describe('compareMobilnost', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareMobilnost(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareMobilnost(entity1, entity2);
        const compareResult2 = service.compareMobilnost(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareMobilnost(entity1, entity2);
        const compareResult2 = service.compareMobilnost(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareMobilnost(entity1, entity2);
        const compareResult2 = service.compareMobilnost(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
