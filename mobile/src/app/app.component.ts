import { Component } from '@angular/core';

import { Platform } from '@ionic/angular';
import { SplashScreen } from '@ionic-native/splash-screen/ngx';
import { StatusBar } from '@ionic-native/status-bar/ngx';
import { MsalService } from './services/msal.service';

@Component({
  selector: 'app-root',
  templateUrl: 'app.component.html'
})
export class AppComponent {
  
  constructor(
    private msalService: MsalService,
    private platform: Platform,
    private splashScreen: SplashScreen,
    private statusBar: StatusBar
  ) {
    this.initializeApp();
  }

  initializeApp() {
    this.platform.ready().then(() => {
      this.statusBar.styleDefault();
      this.splashScreen.hide();
    });
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
}
