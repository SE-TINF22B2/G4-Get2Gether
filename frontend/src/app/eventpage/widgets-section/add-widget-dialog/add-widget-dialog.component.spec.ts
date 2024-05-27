import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddWidgetDialogComponent } from '././add-widget-dialog.component';

describe('AddWidgetComponent', () => {
  let component: AddWidgetDialogComponent;
  let fixture: ComponentFixture<AddWidgetDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AddWidgetDialogComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddWidgetDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
