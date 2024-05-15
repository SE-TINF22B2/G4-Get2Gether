import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EinkauflisteEintragListItemComponent } from './einkaufliste-eintrag-list-item.component';

describe('EinkauflisteEintragListItemComponent', () => {
  let component: EinkauflisteEintragListItemComponent;
  let fixture: ComponentFixture<EinkauflisteEintragListItemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [EinkauflisteEintragListItemComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(EinkauflisteEintragListItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
