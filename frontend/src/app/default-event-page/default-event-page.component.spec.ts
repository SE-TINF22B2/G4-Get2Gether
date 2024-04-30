import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DefaultEventPageComponent } from './default-event-page.component';

describe('DefaultEventPageComponent', () => {
  let component: DefaultEventPageComponent;
  let fixture: ComponentFixture<DefaultEventPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DefaultEventPageComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(DefaultEventPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
