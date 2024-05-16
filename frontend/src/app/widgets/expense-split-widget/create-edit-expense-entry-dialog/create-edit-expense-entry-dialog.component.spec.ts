import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateEditExpenseEntryDialogComponent } from './create-edit-expense-entry-dialog.component';

describe('CreateEditExpenseSplitDialogComponent', () => {
  let component: CreateEditExpenseEntryDialogComponent;
  let fixture: ComponentFixture<CreateEditExpenseEntryDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CreateEditExpenseEntryDialogComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreateEditExpenseEntryDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
