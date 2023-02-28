jest.mock('@ng-bootstrap/ng-bootstrap');

import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ComponentFixture, fakeAsync, inject, TestBed, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { NatjecajDeleteDialogComponent } from 'app/entities/natjecaj/delete/natjecaj-delete-dialog.component';
import { NatjecajService } from 'app/entities/natjecaj/service/natjecaj.service';
import { of } from 'rxjs';
import { ZahtjevService } from '../service/zahtjev.service';
import { ZahtjevDeleteDialogComponent } from './zahtjev-delete-dialog.component';

describe('Zahtjev Management Delete Component', () => {
  let comp: ZahtjevDeleteDialogComponent;
  let fixture: ComponentFixture<ZahtjevDeleteDialogComponent>;
  let service: ZahtjevService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [ZahtjevDeleteDialogComponent],
      providers: [NgbActiveModal],
    })
      .overrideTemplate(ZahtjevDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ZahtjevDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(ZahtjevService);
    mockActiveModal = TestBed.inject(NgbActiveModal);
  });

  describe('confirmDelete', () => {
    it('Should call delete service on confirmDelete', inject(
      [],
      fakeAsync(() => {
        // GIVEN
        jest.spyOn(service, 'delete').mockReturnValue(of(new HttpResponse({ body: {} })));

        // WHEN
        comp.confirmDelete(123);
        tick();

        // THEN
        expect(service.delete).toHaveBeenCalledWith(123);
        expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
      })
    ));

    it('Should not call delete service on clear', () => {
      // GIVEN
      jest.spyOn(service, 'delete');

      // WHEN
      comp.cancel();

      // THEN
      expect(service.delete).not.toHaveBeenCalled();
      expect(mockActiveModal.close).not.toHaveBeenCalled();
      expect(mockActiveModal.dismiss).toHaveBeenCalled();
    });
  });
});
