import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EventsiteComponent } from './eventsite.component';

describe('EventsiteComponent', () => {
  let component: EventsiteComponent;
  let fixture: ComponentFixture<EventsiteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [EventsiteComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(EventsiteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
