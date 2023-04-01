import { Component, OnInit } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';

import { HealthService } from './health.service';
import { Health, HealthDetails, HealthStatus } from './health.model';

@Component({
  selector: 'jhi-health',
  templateUrl: './health.component.html',
})
export class HealthComponent implements OnInit {
  health?: Health;

  constructor(private healthService: HealthService) {}

  ngOnInit(): void {
    this.refresh();
  }

  getBadgeClass(statusState: HealthStatus): string {
    if (statusState === 'UP') {
      return 'badge-success';
    }
    return 'badge-danger';
  }

  refresh(): void {
    this.healthService.checkHealth().subscribe(
      health => (this.health = health),
      (error: HttpErrorResponse) => {
        if (error.status === 503) {
          this.health = error.error;
        }
      }
    );
  }
}
