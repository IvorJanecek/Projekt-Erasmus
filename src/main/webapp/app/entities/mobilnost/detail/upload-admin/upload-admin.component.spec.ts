import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UploadAdminComponent } from './upload-admin.component';

describe('UploadAdminComponent', () => {
  let component: UploadAdminComponent;
  let fixture: ComponentFixture<UploadAdminComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [UploadAdminComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(UploadAdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
