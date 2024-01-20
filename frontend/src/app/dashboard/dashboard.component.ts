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
}
