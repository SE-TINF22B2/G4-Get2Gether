import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SpecificEventPageComponent } from './specific-event-page.component';

describe('SpecificEventPageComponent', () => {
  let component: SpecificEventPageComponent;
  let fixture: ComponentFixture<SpecificEventPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [SpecificEventPageComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(SpecificEventPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
