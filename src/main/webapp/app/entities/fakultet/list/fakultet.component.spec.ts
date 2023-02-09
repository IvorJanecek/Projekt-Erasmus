import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { FakultetService } from '../service/fakultet.service';

import { FakultetComponent } from './fakultet.component';

describe('Fakultet Management Component', () => {
  let comp: FakultetComponent;
  let fixture: ComponentFixture<FakultetComponent>;
  let service: FakultetService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'fakultet', component: FakultetComponent }]), HttpClientTestingModule],
      declarations: [FakultetComponent],
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
      .overrideTemplate(FakultetComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FakultetComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(FakultetService);

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
    expect(comp.fakultets?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to fakultetService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getFakultetIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getFakultetIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
