import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserSettingsItemComponent } from './user-settings-item.component';

describe('UserSettingsItemComponent', () => {
  let component: UserSettingsItemComponent;
  let fixture: ComponentFixture<UserSettingsItemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [UserSettingsItemComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(UserSettingsItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
