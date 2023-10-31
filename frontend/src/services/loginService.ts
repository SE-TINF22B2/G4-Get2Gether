import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { Injectable } from "@angular/core";
import {User} from "../model/user";
import {environment} from "../environment/environment";


@Injectable({
  providedIn: "root",
})
export class LoginService {
  httpOptions = {
    withCredentials: true
  };
  constructor(private http: HttpClient) {}

  getUserData(): Observable<User> {
    return this.http.get<User>(
      `${environment.serverAddress}/user`,
      this.httpOptions
    );
  }

  handleLoginError(error: any) {
    console.log(error);
  }
}
