export class SatelliteSaveDto {
  name?: string;
  missionId?: number;
  launchDate?: Date;

  constructor(data: Partial<SatelliteSaveDto>) {
    this.name = data.name;
    this.missionId = data.missionId;
    this.launchDate = data.launchDate;
  }
}
