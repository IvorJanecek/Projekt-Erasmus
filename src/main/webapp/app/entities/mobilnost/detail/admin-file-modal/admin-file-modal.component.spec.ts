import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminFileModalComponent } from './admin-file-modal.component';

describe('AdminFileModalComponent', () => {
  let component: AdminFileModalComponent;
  let fixture: ComponentFixture<AdminFileModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AdminFileModalComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(AdminFileModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
