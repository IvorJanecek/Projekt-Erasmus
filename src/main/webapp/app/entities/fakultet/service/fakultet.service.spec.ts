import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IFakultet } from '../fakultet.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../fakultet.test-samples';

import { FakultetService } from './fakultet.service';

const requireRestSample: IFakultet = {
  ...sampleWithRequiredData,
};

describe('Fakultet Service', () => {
  let service: FakultetService;
  let httpMock: HttpTestingController;
  let expectedResult: IFakultet | IFakultet[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(FakultetService);
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

    it('should create a Fakultet', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const fakultet = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(fakultet).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Fakultet', () => {
      const fakultet = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(fakultet).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Fakultet', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Fakultet', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Fakultet', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addFakultetToCollectionIfMissing', () => {
      it('should add a Fakultet to an empty array', () => {
        const fakultet: IFakultet = sampleWithRequiredData;
        expectedResult = service.addFakultetToCollectionIfMissing([], fakultet);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(fakultet);
      });

      it('should not add a Fakultet to an array that contains it', () => {
        const fakultet: IFakultet = sampleWithRequiredData;
        const fakultetCollection: IFakultet[] = [
          {
            ...fakultet,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addFakultetToCollectionIfMissing(fakultetCollection, fakultet);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Fakultet to an array that doesn't contain it", () => {
        const fakultet: IFakultet = sampleWithRequiredData;
        const fakultetCollection: IFakultet[] = [sampleWithPartialData];
        expectedResult = service.addFakultetToCollectionIfMissing(fakultetCollection, fakultet);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(fakultet);
      });

      it('should add only unique Fakultet to an array', () => {
        const fakultetArray: IFakultet[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const fakultetCollection: IFakultet[] = [sampleWithRequiredData];
        expectedResult = service.addFakultetToCollectionIfMissing(fakultetCollection, ...fakultetArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const fakultet: IFakultet = sampleWithRequiredData;
        const fakultet2: IFakultet = sampleWithPartialData;
        expectedResult = service.addFakultetToCollectionIfMissing([], fakultet, fakultet2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(fakultet);
        expect(expectedResult).toContain(fakultet2);
      });

      it('should accept null and undefined values', () => {
        const fakultet: IFakultet = sampleWithRequiredData;
        expectedResult = service.addFakultetToCollectionIfMissing([], null, fakultet, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(fakultet);
      });

      it('should return initial array if no Fakultet is added', () => {
        const fakultetCollection: IFakultet[] = [sampleWithRequiredData];
        expectedResult = service.addFakultetToCollectionIfMissing(fakultetCollection, undefined, null);
        expect(expectedResult).toEqual(fakultetCollection);
      });
    });

    describe('compareFakultet', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareFakultet(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareFakultet(entity1, entity2);
        const compareResult2 = service.compareFakultet(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareFakultet(entity1, entity2);
        const compareResult2 = service.compareFakultet(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareFakultet(entity1, entity2);
        const compareResult2 = service.compareFakultet(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
