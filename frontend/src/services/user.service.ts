import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable, ReplaySubject} from "rxjs";
import {User} from "../model/user";
import {environment} from "../environment/environment";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  user = new ReplaySubject<User | null>(1);

  constructor(private http: HttpClient) {
  }

  init() {
    return this.fetchUserModel().subscribe({
      next: user => {
        console.log("Loaded user model", user);
        this.user.next(user);
      },
      error: () => {
        this.user.next(null);
      }
    });
  }

  fetchUserModel(): Observable<User> {
    return this.http.get<User>(`${environment.api}/user`, {withCredentials: true});
  }

}
