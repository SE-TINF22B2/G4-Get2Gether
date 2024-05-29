import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeleteCarConfirmationDialogComponent } from './delete-car-confirmation-dialog.component';

describe('DeleteCarConfirmationDialogComponent', () => {
  let component: DeleteCarConfirmationDialogComponent;
  let fixture: ComponentFixture<DeleteCarConfirmationDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DeleteCarConfirmationDialogComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(DeleteCarConfirmationDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
