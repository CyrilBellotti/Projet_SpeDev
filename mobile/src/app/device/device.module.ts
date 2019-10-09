import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Routes, RouterModule } from '@angular/router';
import { ChartsModule } from 'ng2-charts';

import { IonicModule } from '@ionic/angular';

import { DevicePage } from './device.page';

const routes: Routes = [
  {
    path: '',
    component: DevicePage
  }
];

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    RouterModule.forChild(routes),
    ChartsModule
  ],
  declarations: [DevicePage]
})
export class DevicePageModule {}
