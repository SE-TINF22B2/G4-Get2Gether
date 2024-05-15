import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditEintragDialogComponent } from './edit-eintrag-dialog.component';

describe('EditEintragDialogComponent', () => {
  let component: EditEintragDialogComponent;
  let fixture: ComponentFixture<EditEintragDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [EditEintragDialogComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(EditEintragDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
