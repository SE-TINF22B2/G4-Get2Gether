import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddEntryDialogComponent } from './add-entry-dialog.component';

describe('AddEintragDialogComponent', () => {
  let component: AddEntryDialogComponent;
  let fixture: ComponentFixture<AddEntryDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AddEntryDialogComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddEntryDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
