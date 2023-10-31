import { Injectable } from "@angular/core";
import { BehaviorSubject } from "rxjs";
import {User} from "../model/user";

@Injectable({
  providedIn: "root",
})
export class AppStateService {
  user = new BehaviorSubject<User | null>(null);

  constructor() {}
}
