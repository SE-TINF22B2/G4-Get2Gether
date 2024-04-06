import {Component, OnInit} from '@angular/core';
import {GuardsCheckEnd, GuardsCheckStart, NavigationCancel, Router} from "@angular/router";
import {animate, style, transition, trigger} from "@angular/animations";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
  animations: [
    trigger("fadeInOut", [
      transition(":enter", [
        style({opacity: 0}),
        animate("0.5s ease-in", style({opacity: 1}))
      ]),
      transition(":leave", [
        style({opacity: 1}),
        animate("0.5s ease-out", style({opacity: 0}))
      ])
    ])
  ]
})
export class AppComponent implements OnInit {

  isLoading = false;

  constructor(private router: Router) {
  }

  ngOnInit() {
    this.router.events.subscribe(event => {
      if (event instanceof GuardsCheckStart) {
        this.isLoading = true;
      } else if (event instanceof GuardsCheckEnd || event instanceof NavigationCancel) {
        this.isLoading = false;
      }
    });
  }

}
