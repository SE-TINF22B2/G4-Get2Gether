# Frontend

This project was generated with [Angular CLI](https://github.com/angular/angular-cli) version 17.1.0.

> [!TIP]
> 📖 [Hier geht's zur technischen Dokumentation des Frontends.](https://github.com/SE-TINF22B2/G4-Get2Gether/wiki/Frontend-Dokumentation)

## Getting Started
Voraussetzung:
- NodeJS mit dem Node Package Manager (npm) ist installiert
- Angular CLI ist installiert (`npm install -g @angular/cli`)

Bevor die Anwendung zum ersten Mal gestartet werden kann, müssen alle Dependencies installiert werden:
```bash
npm install
```

Danach kann die Anwendung lokal mit `ng serve` gestartet werden.

### Development server

Run `ng serve` for a dev server. Navigate to `http://localhost:4200/`. The application will automatically reload if you change any of the source files.

### Code scaffolding

Run `ng generate component component-name` to generate a new component. You can also use `ng generate directive|pipe|service|class|guard|interface|enum|module`.

### Build

Run `ng build` to build the project. The build artifacts will be stored in the `dist/` directory.

### Running unit tests

Run `ng test` to execute the unit tests via [Karma](https://karma-runner.github.io).

### Running end-to-end tests

Run `ng e2e` to execute the end-to-end tests via a platform of your choice. To use this command, you need to first add a package that implements end-to-end testing capabilities.

### Further help

To get more help on the Angular CLI use `ng help` or go check out the [Angular CLI Overview and Command Reference](https://angular.io/cli) page.

---
## Repsonsive Design
Um ein anpassungsfähiges Frontend zu erstellen, verwenden wir den Breakpoint Observer von Angular CDK. Damit können wir verschiedene Fenstergrößen überwachen und je nach Bedarf CSS-Klassen zu einer Komponente hinzufügen, entfernen oder die Komponente ausblenden. Mehr Informationen zur Anwendung findest du [hier](https://blog.angular-university.io/angular-responsive-design/).
### Anwendungsbeispiel 
`login.component.ts`:
```typescript
constructor(public breakpointObserver: BreakpointObserver) {}

ngOnInit() {
  this.breakpointObserver.observe(Breakpoints.HandsetPortrait)
    .subscribe(result => {
      this.isPhonePortrait = result.matches;
    })
}
```
`login.component.html`:
```html
<div class="login-centered" [ngClass]="{'is-phone-portrait': isPhonePortrait}">
  <span>Willkommen bei Get2Gether!</span>
```

