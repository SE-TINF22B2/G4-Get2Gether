import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddCarpoolDialogComponent } from './add-carpool-dialog.component';

describe('AddCarpoolDialogComponent', () => {
  let component: AddCarpoolDialogComponent;
  let fixture: ComponentFixture<AddCarpoolDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AddCarpoolDialogComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AddCarpoolDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
