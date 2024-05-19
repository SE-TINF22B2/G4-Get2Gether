import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditEntryDialogComponent } from './edit-entry-dialog.component';

describe('EditEintragDialogComponent', () => {
  let component: EditEntryDialogComponent;
  let fixture: ComponentFixture<EditEntryDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [EditEntryDialogComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EditEntryDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
