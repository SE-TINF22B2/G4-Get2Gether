import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ExpenseDebtOverviewComponent } from './expense-debt-overview.component';

describe('ExpenseDebtOverviewComponent', () => {
  let component: ExpenseDebtOverviewComponent;
  let fixture: ComponentFixture<ExpenseDebtOverviewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ExpenseDebtOverviewComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ExpenseDebtOverviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
