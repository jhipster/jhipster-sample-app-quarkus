export type HealthStatus = 'UP' | 'DOWN' | 'UNKNOWN' | 'OUT_OF_SERVICE';

export interface Health {
  status: HealthStatus;
  checks: HealthDetails[];
}

export interface HealthDetails {
  name: string;
  status: HealthStatus;
}
