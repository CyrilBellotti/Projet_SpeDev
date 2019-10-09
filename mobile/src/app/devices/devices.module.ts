import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Routes, RouterModule } from '@angular/router';

import { IonicModule } from '@ionic/angular';
import { ChartsModule } from 'ng2-charts';

import { DevicesPage } from './devices.page';
import { DevicePage } from '../device/device.page';

const routes: Routes = [
  {
    path: '',
    component: DevicesPage
  },
  {
    path: ':id',
    component: DevicePage
  },
];

@NgModule({
  imports: [
    ChartsModule,
    CommonModule,
    FormsModule,
    IonicModule,
    RouterModule.forChild(routes)
  ],
  declarations: [DevicesPage, DevicePage]
})
export class DevicesPageModule {}
