import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DefaultCarpoolPageComponent } from './default-carpool-page.component';

describe('DefaultCarpoolPageComponent', () => {
  let component: DefaultCarpoolPageComponent;
  let fixture: ComponentFixture<DefaultCarpoolPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DefaultCarpoolPageComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(DefaultCarpoolPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
