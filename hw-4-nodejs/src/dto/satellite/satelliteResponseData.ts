export interface SatelliteResponseDto {
    _id: string;
    name: string;
    launchDate: Date;
    missionId: string;
    capacity?: number;
    orbitType?: string;
  }