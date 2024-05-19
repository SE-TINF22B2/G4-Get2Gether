import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ShoppingListWidgetComponent } from './shopping-list-widget.component';

describe('EinkaufslisteWidgetComponent', () => {
  let component: ShoppingListWidgetComponent;
  let fixture: ComponentFixture<ShoppingListWidgetComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ShoppingListWidgetComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ShoppingListWidgetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
