import { Component } from '@angular/core';
import {NgOptimizedImage} from "@angular/common";
import {MatProgressSpinner} from "@angular/material/progress-spinner";
import {MatProgressBar} from "@angular/material/progress-bar";

@Component({
  selector: 'app-splashscreen',
  standalone: true,
  imports: [
    NgOptimizedImage,
    MatProgressSpinner,
    MatProgressBar
  ],
  templateUrl: './splashscreen.component.html',
  styleUrl: './splashscreen.component.scss'
})
export class SplashscreenComponent {

}
