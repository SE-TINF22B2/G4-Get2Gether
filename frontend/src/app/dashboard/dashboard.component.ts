import { Component } from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent {
  constructor(private http: HttpClient) {
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
}
