import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { SharedModule } from 'app/shared/shared.module';

import { HealthComponent } from './health.component';
import { healthRoute } from './health.route';

@NgModule({
  imports: [SharedModule, RouterModule.forChild([healthRoute])],
  declarations: [HealthComponent],
})
export class HealthModule {}
