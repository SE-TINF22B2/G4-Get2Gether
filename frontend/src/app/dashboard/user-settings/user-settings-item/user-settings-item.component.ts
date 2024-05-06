import {Component, Input} from '@angular/core';
import {EventOverview} from "../../../../model/event";
import {User} from "../../../../model/user";
import {HttpClient} from "@angular/common/http";
import {UserService} from "../../../../services/user.service";

@Component({
  selector: 'app-user-settings-item',
  templateUrl: './user-settings-item.component.html',
  styleUrl: './user-settings-item.component.scss'
})
export class UserSettingsItemComponent {
  constructor(
    public userService: UserService,
    private http: HttpClient
  ) {
    if (document.getElementById('colorcard')!=null){
      // @ts-ignore
      document.getElementById('colorcard')
        .setAttribute("background",mat.get-theme-color(this.color, primary))
    }
  }

  @Input()
  color!: string;


  switchColor() {
    document.documentElement.setAttribute("theme", this.color);
    this.http.put("http://localhost:8080/user/self", {
      "settings": {
        "colorMode" : this.color
      }
    },{withCredentials: true}).subscribe(result =>{
      console.log(result);
    })
  }
}
