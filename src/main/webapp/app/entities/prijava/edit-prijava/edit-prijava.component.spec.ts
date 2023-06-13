import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditPrijavaComponent } from './edit-prijava.component';

describe('EditPrijavaComponent', () => {
  let component: EditPrijavaComponent;
  let fixture: ComponentFixture<EditPrijavaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [EditPrijavaComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(EditPrijavaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
