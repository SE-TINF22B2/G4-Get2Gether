import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CarpoolWidgetComponent } from './carpool-widget.component';

describe('CarpoolWidgetComponent', () => {
  let component: CarpoolWidgetComponent;
  let fixture: ComponentFixture<CarpoolWidgetComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CarpoolWidgetComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CarpoolWidgetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
