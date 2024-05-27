import {Component, Inject, Input} from '@angular/core';
import {MAT_DIALOG_DATA} from "@angular/material/dialog";

@Component({
  selector: 'app-fehlerhandling',
  templateUrl: './fehlerhandling.component.html',
  styleUrl: './fehlerhandling.component.scss'
})
export class FehlerhandlingComponent {
  errorMessage: string;

   constructor(@Inject(MAT_DIALOG_DATA) data: {error: any}) {
     this.errorMessage = data.error;
   }

}
