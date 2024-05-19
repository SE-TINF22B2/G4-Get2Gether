import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ShoppingListEntryListItemComponent } from './shopping-list-entry-list-item.component';

describe('EinkauflisteEintragListItemComponent', () => {
  let component: ShoppingListEntryListItemComponent;
  let fixture: ComponentFixture<ShoppingListEntryListItemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ShoppingListEntryListItemComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ShoppingListEntryListItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
