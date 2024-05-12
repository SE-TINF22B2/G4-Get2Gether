import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddAuftragDialogComponent } from './add-auftrag-dialog.component';

describe('AddAuftragDialogComponent', () => {
  let component: AddAuftragDialogComponent;
  let fixture: ComponentFixture<AddAuftragDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AddAuftragDialogComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AddAuftragDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
