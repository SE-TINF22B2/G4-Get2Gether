import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FehlerhandlingComponent } from './fehlerhandling.component';

describe('FehlerhandlingComponent', () => {
  let component: FehlerhandlingComponent;
  let fixture: ComponentFixture<FehlerhandlingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [FehlerhandlingComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(FehlerhandlingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
