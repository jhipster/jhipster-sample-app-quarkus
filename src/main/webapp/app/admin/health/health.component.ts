import { Component, OnInit } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';

import SharedModule from 'app/shared/shared.module';
import { HealthService } from './health.service';
import { Health, HealthStatus } from './health.model';

@Component({
  standalone: true,
  selector: 'jhi-health',
  templateUrl: './health.component.html',
  imports: [SharedModule],
})
export default class HealthComponent implements OnInit {
  health?: Health;

  constructor(private healthService: HealthService) {}

  ngOnInit(): void {
    this.refresh();
  }

  getBadgeClass(statusState: HealthStatus): string {
    if (statusState === 'UP') {
      return 'bg-success';
    }
    return 'bg-danger';
  }

  refresh(): void {
    this.healthService.checkHealth().subscribe({
      next: health => (this.health = health),
      error: (error: HttpErrorResponse) => {
        if (error.status === 503) {
          this.health = error.error;
        }
      },
    });
  }
}
