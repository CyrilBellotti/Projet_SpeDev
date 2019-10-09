import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http'
import { HomeService } from '../home/home.service';
import { MsalService } from '../services/msal.service';
import { NativePageTransitions, NativeTransitionOptions } from '@ionic-native/native-page-transitions/ngx';

@Component({
  selector: 'app-devices',
  templateUrl: './devices.page.html',
  styleUrls: ['./devices.page.scss'],
})
export class DevicesPage implements OnInit {

  devices: any;

  constructor(private msalService: MsalService, private route: Router, public httpClient: HttpClient, public homeService: HomeService) { }

  ngOnInit() {
    if (!this.msalService.isLoggedIn()) {
      return;
    }
    if (!this.homeService.user) {
      this.route.navigateByUrl('/home');
    }
    this.devices = this.homeService.user.device;
  }

  goToDevice(device: any) {
    this.route.navigateByUrl('/devices/' + device.uid);
  }

  logout(){
    this.msalService.logout();
  }

  // options:  NativeTransitionOptions = {
  //   direction: 'up',
  //   duration: 500,
  //   slowdownfactor: 3,
  //   slidePixels: 20,
  //   iosdelay: 100,
  //   androiddelay: 150,
  //   fixedPixelsTop: 0,
  //   fixedPixelsBottom: 60
  //  }

  back() {
    // this.nativePageTransitions.slide(this.options);
    this.route.navigateByUrl("/home")
  }

  // ionViewWillLeave() {

     
   
  //   this.nativePageTransitions.slide(options)
  //     .then()
  //     .catch();
  //  }
}