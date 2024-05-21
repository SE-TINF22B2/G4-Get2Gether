import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CarpoolCardItemComponent } from './carpool-card-item.component';

describe('CarpoolCardItemComponent', () => {
  let component: CarpoolCardItemComponent;
  let fixture: ComponentFixture<CarpoolCardItemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CarpoolCardItemComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CarpoolCardItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
