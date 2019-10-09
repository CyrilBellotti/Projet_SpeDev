import { Component, OnInit } from '@angular/core';
import { MsalService } from '../services/msal.service';
import { Router } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { HomeService } from '../home/home.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.page.html',
  styleUrls: ['./login.page.scss'],
})
export class LoginPage implements OnInit {

  constructor(
    private msalService: MsalService,
    private route: Router,
    public httpClient: HttpClient,
    public homeService: HomeService
  ) { }

  ngOnInit() {
    if (this.msalService.isLoggedIn()) {
      this.httpClient.get('http://localhost:8080/users/mail/' + this.msalService.getUserEmail()).subscribe((val) => {
        this.homeService.user = val;
        if (!val) {
          this.httpClient.post("http://localhost:8080/users/createUser/", { name: this.msalService.getUserEmail(), mail: this.msalService.getUserEmail() }).subscribe((val2) => {  
            this.homeService.user = val2;
          }, error => {
            console.log(error)
          })
        }
        }, error => {
          console.log(error)
        }
      )
    }
  }

  useremail(){
    let useremail = this.msalService.getUserEmail();
    return useremail;
  }

  login(){
    this.msalService.login();
  }

  signup(){
    this.msalService.signup();
  }

  logout(){
    this.msalService.logout();
  }

  isUserLoggedIn(){
    return this.msalService.isLoggedIn();
  }

  goToHome() {
    this.route.navigateByUrl('/home')
  }
}
