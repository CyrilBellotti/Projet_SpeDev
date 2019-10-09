import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { HomeService } from '../home/home.service';
import { MsalService } from '../services/msal.service';
import { ToastController } from '@ionic/angular';

@Component({
  selector: 'app-device',
  templateUrl: './device.page.html',
  styleUrls: ['./device.page.scss'],
})
export class DevicePage implements OnInit {

  device: { id: number; sensor: any[]; uid: string; }

  
  constructor(public toastController: ToastController, private msalService: MsalService, private activatedRoute: ActivatedRoute, private route: Router, public httpClient: HttpClient, public homeService: HomeService) { }
  sensorSelected: string;

  ngOnInit() {
    if (!this.msalService.isLoggedIn()) {
      this.route.navigateByUrl('/login')
      return;
    }
    this.sensorSelected = "light"
    // this.listSensorAction.push({ sensor: "light", actionEvent: false })
    this.httpClient.get("http://localhost:8080/devices/uid/" + this.activatedRoute.snapshot.paramMap.get("id")).subscribe((val) => {  
        this.homeService.device = val;
        this.device = this.homeService.device;
      }, error => {
        console.log(error)
      }
    )
  }

  segmentChanged(e) {
    this.actualState = e.detail.value
  }

  type: String;
  uidSensor: String;
  statsSensorSelected: String;
  statsTypeCalcul: String;
  statsDuree: String;
  actualState: string = 'list';
  // listSensorAction: [];
  sensorActive = false;


  async runActionOnDevice() {
    if (this.sensorSelected == 'light') {
      // this.listSensorAction.forEach(element => {
        // console.log(element)
        // if (element.sensor == 'light') {
        //   this.type = element.type;
        //   this.uidSensor = element.uid;
        // }
      // });
      const toast = await this.toastController.create({
        message: 'Your order has been send with success.',
        color: 'success',
        duration: 2000
      });
      const toastError = await this.toastController.create({
        message: 'Failed to send your order.',
        color: 'danger',
        duration: 2000
      });
      let val = "0";
      if (this.sensorActive) {
        this.sensorActive = false;
        val = "0";
      } else {
        this.sensorActive = true;
        val = "1"
      }
      // this.sensorActive = !this.sensorActive;
      this.httpClient.post("http://localhost:8080/devices/action/", { sensor: "sensor", actionEvent: val }).subscribe((val) => {  
        console.log(val)
        toast.present();
      }, error => {
        toastError.present();
        console.log(error)
      }
    )
    }
  }

  duree: string | number;
  value: { value: number; date: string; }[];
  test: boolean;

  async loadStat() {
    let typeCapteur: any;
    this.homeService.device.sensors.forEach(element => {
      if (element.uid == this.statsSensorSelected) {
        typeCapteur = element.type
      }
    });
    if (!this.statsTypeCalcul || !this.statsDuree || !this.statsSensorSelected) {
      const toastError = await this.toastController.create({
        message: 'Select all information before asking to show graphics.',
        color: 'primary',
        duration: 3000
      });
      toastError.present();
      return;
    }
    let data = null;
    if (this.statsDuree === "oneDay") {
      this.duree = 24;
    } else if (this.statsDuree === "oneWeek") {
      this.duree = 168;
    } else if (this.statsDuree === "oneMonth") {
      this.duree = 720;
    }
    if (this.statsTypeCalcul === "average") {
      data = "Moyenne"
    }
    this.httpClient.get("http://localhost:8080/devices/sensor/" + this.statsSensorSelected + "/duration/" + this.duree + "/calculType/" + data).subscribe((val) => {  
        console.log(val, val[0])
        const lineChartDataArray = [];
        lineChartDataArray.length = 0;
        this.lineChartLabels.length = 0;
        Object.values(val).forEach(element => {
          const date = new Date(element.date) 
          var day = date.getDate();
          var monthIndex = date.getMonth();
          var year = date.getFullYear();
          var minutes = date.getMinutes();
          var hours = date.getHours();
          var seconds = date.getSeconds();
          if (this.statsDuree === "oneDay") {
            var myFormattedDate = hours+":"+minutes+":"+seconds;
          } else {
            var myFormattedDate = year+"-"+(monthIndex+1)+"-"+day+ " " + hours+":"+minutes+":"+seconds;
          }
          this.lineChartLabels.push(myFormattedDate);
          lineChartDataArray.push(element.value);
        });
        this.value = [
          {
            value: 12,
            date: "12h"
          },
          {
            value: 13,
            date: "13h"
          },
          {
            value: 11,
            date: "14h"
          },
          {
            value: 16,
            date: "15h"
          },
          {
            value: 19,
            date: "16h"
          }
        ]
      
        // this.value.forEach(e => {
        //   this.lineChartLabels.push(e.date);
        //   lineChartDataArray.push(e.value);
        // })
        this.lineChartData = [
          {data: lineChartDataArray, label: typeCapteur}
        ];
        this.test = true;
      }, error => {
        console.log(error)
      }
    )
  }


  public lineChartData:Array<any> = [];
  public lineChartLabels:Array<any> = [];
  public lineChartOptions:any = {
    responsive: true
  };
  public lineChartColors:Array<any> = [
    { // grey
      backgroundColor: 'rgba(148,159,177,0.2)',
      borderColor: 'rgba(148,159,177,1)',
      pointBackgroundColor: 'rgba(148,159,177,1)',
      pointBorderColor: '#fff',
      pointHoverBackgroundColor: '#fff',
      pointHoverBorderColor: 'rgba(148,159,177,0.8)'
    },
    { // dark grey
      backgroundColor: 'rgba(77,83,96,0.2)',
      borderColor: 'rgba(77,83,96,1)',
      pointBackgroundColor: 'rgba(77,83,96,1)',
      pointBorderColor: '#fff',
      pointHoverBackgroundColor: '#fff',
      pointHoverBorderColor: 'rgba(77,83,96,1)'
    },
    { // grey
      backgroundColor: 'rgba(148,159,177,0.2)',
      borderColor: 'rgba(148,159,177,1)',
      pointBackgroundColor: 'rgba(148,159,177,1)',
      pointBorderColor: '#fff',
      pointHoverBackgroundColor: '#fff',
      pointHoverBorderColor: 'rgba(148,159,177,0.8)'
    }
  ];
  public lineChartLegend:boolean = true;
  public lineChartType:string = 'line';
  
  public randomize():void {
    let _lineChartData:Array<any> = new Array(this.lineChartData.length);
    for (let i = 0; i < this.lineChartData.length; i++) {
      _lineChartData[i] = {data: new Array(this.lineChartData[i].data.length), label: this.lineChartData[i].label};
      for (let j = 0; j < this.lineChartData[i].data.length; j++) {
        _lineChartData[i].data[j] = Math.floor((Math.random() * 100) + 1);
      }
    }
    this.lineChartData = _lineChartData;
  }
  
  // events
  public chartClicked(e:any):void {
  }
  
  public chartHovered(e:any):void {
  }

  logout(){
    this.msalService.logout();
  }

  back() {
    this.route.navigateByUrl('/devices');
  }
}
