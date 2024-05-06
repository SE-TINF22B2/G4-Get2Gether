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

  ngOnInit(){
    console.log(this.color)
    //set colorcard
    if (document.getElementById(this.color)!=null) {
      // @ts-ignore
      let element: HTMLElement = document.getElementById(this.color)
      if (this.color == "light") {
        element.style.backgroundColor= mat.get'var(--light-default-primary)'
        console.log("in light"+ element.getAttributeNames())
      } else if (this.color == "dark") {
        element.style.backgroundColor= 'var(--dark-default-primary)'
        console.log("in dark")
      } else {
        element.style.backgroundColor= 'var(--error-color)'
        console.log("in error")
      }
    }else{
      console.log("no such element")
    }
  }
  //switch color on change
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
