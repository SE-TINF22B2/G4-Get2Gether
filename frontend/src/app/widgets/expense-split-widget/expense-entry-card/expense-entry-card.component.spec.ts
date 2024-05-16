import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ExpenseEntryCardComponent } from './expense-entry-card.component';

describe('ExpenseEntryCardComponent', () => {
  let component: ExpenseEntryCardComponent;
  let fixture: ComponentFixture<ExpenseEntryCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ExpenseEntryCardComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ExpenseEntryCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
