import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ZahtjevService } from '../service/zahtjev.service';

import { ZahtjevComponent } from './zahtjev.component';

describe('Zahtjev Management Component', () => {
  let comp: ZahtjevComponent;
  let fixture: ComponentFixture<ZahtjevComponent>;
  let service: ZahtjevService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'zahtjev', component: ZahtjevComponent }]), HttpClientTestingModule],
      declarations: [ZahtjevComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            data: of({
              defaultSort: 'id,asc',
            }),
            queryParamMap: of(
              jest.requireActual('@angular/router').convertToParamMap({
                page: '1',
                size: '1',
                sort: 'id,desc',
              })
            ),
            snapshot: { queryParams: {} },
          },
        },
      ],
    })
      .overrideTemplate(ZahtjevComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ZahtjevComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(ZahtjevService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.zahtjevs?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to zahtjevService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getZahtjevIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getZahtjevIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
