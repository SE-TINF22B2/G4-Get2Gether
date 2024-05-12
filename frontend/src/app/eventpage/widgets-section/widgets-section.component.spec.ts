import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WidgetsSectionComponent } from './widgets-section.component';

describe('WidgetsSectionComponent', () => {
  let component: WidgetsSectionComponent;
  let fixture: ComponentFixture<WidgetsSectionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [WidgetsSectionComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(WidgetsSectionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
