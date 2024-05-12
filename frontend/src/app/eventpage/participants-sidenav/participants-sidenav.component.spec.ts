import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ParticipantsSidenavComponent } from './participants-sidenav.component';

describe('ParticipantsSidenavComponent', () => {
  let component: ParticipantsSidenavComponent;
  let fixture: ComponentFixture<ParticipantsSidenavComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ParticipantsSidenavComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ParticipantsSidenavComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
