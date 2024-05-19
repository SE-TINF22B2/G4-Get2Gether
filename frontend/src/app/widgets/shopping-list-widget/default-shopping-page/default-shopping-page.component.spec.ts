import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DefaultShoppingPageComponent } from './default-shopping-page.component';

describe('DefaultPageComponent', () => {
  let component: DefaultShoppingPageComponent;
  let fixture: ComponentFixture<DefaultShoppingPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DefaultShoppingPageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DefaultShoppingPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
