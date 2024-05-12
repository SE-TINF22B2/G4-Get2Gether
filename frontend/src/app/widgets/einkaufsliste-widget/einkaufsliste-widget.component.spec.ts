import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EinkaufslisteWidgetComponent } from './einkaufsliste-widget.component';

describe('EinkaufslisteWidgetComponent', () => {
  let component: EinkaufslisteWidgetComponent;
  let fixture: ComponentFixture<EinkaufslisteWidgetComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [EinkaufslisteWidgetComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(EinkaufslisteWidgetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
