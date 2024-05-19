import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable, ReplaySubject, tap} from "rxjs";
import {ColorMode, User} from "../model/user";
import {environment} from "../environment/environment";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  user = new ReplaySubject<User | null>(1);

  constructor(private http: HttpClient) {
  }

  private updateUserModel(user: User) {
    this.user.next(user);
    if (user.settings && user.settings.colorMode) {
      this.applyThemeAttribute(user.settings.colorMode);
    }
  }

  init() {
    return this.fetchUserModel().subscribe({
      next: user => {
        console.log("Loaded user model", user);
        this.updateUserModel(user);
      },
      error: () => {
        this.user.next(null);
      }
    });
  }

  fetchUserModel(): Observable<User> {
    return this.http.get<User>(`${environment.api}/user`, {withCredentials: true});
  }

  updateTheme(colorMode: ColorMode): Observable<User> {
    return this.http.put<User>(`${environment.api}/user/self`, {
      settings: {
        colorMode: colorMode
      }
    }, {withCredentials: true}).pipe(
      tap(value => this.updateUserModel(value))
    );
  }

  applyThemeAttribute(colorMode: ColorMode) {
    document.documentElement.setAttribute("theme", colorMode.toLowerCase());
  }

}
