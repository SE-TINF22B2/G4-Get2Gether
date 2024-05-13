import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddEintragDialogComponent } from './add-eintrag-dialog.component';

describe('AddEintragDialogComponent', () => {
  let component: AddEintragDialogComponent;
  let fixture: ComponentFixture<AddEintragDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AddEintragDialogComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddEintragDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
