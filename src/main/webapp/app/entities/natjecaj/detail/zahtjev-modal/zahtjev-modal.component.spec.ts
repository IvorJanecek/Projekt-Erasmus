import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ZahtjevModalComponent } from './zahtjev-modal.component';

describe('ZahtjevModalComponent', () => {
  let component: ZahtjevModalComponent;
  let fixture: ComponentFixture<ZahtjevModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ZahtjevModalComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(ZahtjevModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
