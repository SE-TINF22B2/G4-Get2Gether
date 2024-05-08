import {Component, Input, ViewChild} from '@angular/core';
import {EventOverview} from "../../../../model/event";
import {User} from "../../../../model/user";
import {HttpClient} from "@angular/common/http";
import {UserService} from "../../../../services/user.service";
import {iconButton} from "material-components-web/index";

@Component({
  selector: 'app-user-settings-item',
  templateUrl: './user-settings-item.component.html',
  styleUrl: './user-settings-item.component.scss'
})
export class UserSettingsItemComponent {
  constructor(
    public userService: UserService,
    private http: HttpClient
  ) {}

  @Input()
  color!: string;

  //switch color on change
  switchColor() {
    document.documentElement.setAttribute("theme", this.color);
    console.log("curr ColorMode: "+document.documentElement.getAttribute("theme"))
    let colormode:string=this.color.replace("-","_").toUpperCase();
    this.http.put("http://localhost:8080/user/self", {
      "settings": {
        "colorMode" : colormode
      }
    },{withCredentials: true}).subscribe(result =>{
      console.log(result);
    })
  }
}
