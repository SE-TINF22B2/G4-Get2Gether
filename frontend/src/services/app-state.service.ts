import {EventEmitter, Injectable} from "@angular/core";

@Injectable({
  providedIn: "root",
})
export class AppStateService {

  constructor() {}

  doUpdateEventList = new EventEmitter();
}
