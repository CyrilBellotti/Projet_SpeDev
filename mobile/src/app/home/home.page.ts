import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { HomeService } from './home.service';
import { Router } from '@angular/router';
import { MsalService } from '../services/msal.service';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-home',
  templateUrl: 'home.page.html',
  styleUrls: ['home.page.scss'],
})
export class HomePage implements OnInit {

  private headers = new HttpHeaders('Content-Type: application/x-www-form-urlencoded; charset=UTF-8');

  constructor(private msalService: MsalService, private route: Router, public httpClient: HttpClient, public homeService: HomeService) { }

  results: Observable<any>;

  ngOnInit() {
    if (!this.msalService.isLoggedIn()) {
      this.route.navigateByUrl('/login')
      return;
    }
    this.httpClient.get('http://localhost:8080/users/mail/' + this.msalService.getUserEmail()).subscribe((val) => {
        this.homeService.user = val;
      }, error => {
        console.log(error)
      }
    )
  }

  loadMyUser() {
  }

  goToDevices() {
    this.route.navigateByUrl('/devices');
  }

  logout() {
    this.msalService.logout();
  }
}
