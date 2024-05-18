import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeleteEntryConfirmationDialogComponent } from './delete-entry-confirmation-dialog.component';

describe('DeleteEntryConfirmationDialogComponent', () => {
  let component: DeleteEntryConfirmationDialogComponent;
  let fixture: ComponentFixture<DeleteEntryConfirmationDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DeleteEntryConfirmationDialogComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(DeleteEntryConfirmationDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
