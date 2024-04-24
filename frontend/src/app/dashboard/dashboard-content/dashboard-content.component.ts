import { Component } from '@angular/core';
import {UserService} from "../../../services/user.service";
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-dashboard-content',
  templateUrl: './dashboard-content.component.html',
  styleUrl: './dashboard-content.component.scss'
})
export class DashboardContentComponent {

  constructor(
    public userService: UserService,
    private http: HttpClient
  ) {
  }

  afterAuth(){
    this.http.get("http://localhost:8080/user", {withCredentials: true}).subscribe(result =>{
      console.log(result);
    })
  }

  createEvent(){
    this.http.post("http://localhost:8080/event/", {
      "name": "Geiles Event7",
      "description": "Wird mega",
      "location": "Elkes Kueche",
      "date": "1986-04-08T12:30:00"
    },{withCredentials: true}).subscribe(result =>{
      console.log(result);
    })
  }

  getOwn(){
    this.http.get("http://localhost:8080/event/own", {withCredentials: true}).subscribe(result =>{
      console.log(result);
    })
  }

  generateInvitationLink(){
    this.http.get("http://localhost:8080/event/d1541053-cea8-4f31-8500-d5151d0853c8/generateInvitationLink", {withCredentials: true}).subscribe(result =>{
      console.log(result);
    })
  }

  updateEvent(){
    this.http.put("http://localhost:8080/event/d1541053-cea8-4f31-8500-d5151d0853c8", {
      "name": "Geiles Event 17",
      "description": "Wird mega geil",
      "location": "Elkes Kuechen",
      "date": "1987-04-08T12:30:00"
    },{withCredentials: true}).subscribe(result =>{
      console.log(result);
    })
  }
  updateUserColor( colormode: string){
    this.http.put("http://localhost:8080/user/self", {
      "settings": {
        "colorMode" : colormode
      }
    },{withCredentials: true}).subscribe(result =>{
      console.log(result);
    })
  }
}
