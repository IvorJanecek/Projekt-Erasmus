import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ZahtjevUpdateComponent } from './zahtjev-update.component';

describe('ZahtjevUpdateComponent', () => {
  let component: ZahtjevUpdateComponent;
  let fixture: ComponentFixture<ZahtjevUpdateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ZahtjevUpdateComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(ZahtjevUpdateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
