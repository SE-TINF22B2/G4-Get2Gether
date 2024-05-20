import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ExpenseSplitWidgetComponent } from './expense-split-widget.component';

describe('ExpenseSplitWidgetComponent', () => {
  let component: ExpenseSplitWidgetComponent;
  let fixture: ComponentFixture<ExpenseSplitWidgetComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ExpenseSplitWidgetComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ExpenseSplitWidgetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
