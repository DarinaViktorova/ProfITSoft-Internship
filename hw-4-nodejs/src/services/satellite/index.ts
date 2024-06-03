import Satellite, { ISatellite } from "../../model/satellite";
import { SatelliteSaveDto } from "../../dto/satellite/satelliteSaveDto";
import axios from "axios";
import { Types } from "mongoose";

const MISSION_SERVICE_URL = 'http://localhost:8080/api/missions';

export const createSatellite = async (
  satelliteDto: SatelliteSaveDto
): Promise<string> => {
  if (typeof satelliteDto.missionId !== "number") {
    throw new Error("Mission ID is required and must be a number");
  }

  await validateMission(satelliteDto.missionId);
  const satellite = await new Satellite(satelliteDto).save();
  return (satellite._id as Types.ObjectId).toString(); 
};

export const listSatellitesByMissionId = async (
  missionId: number,
  size: number,
  from: number
): Promise<ISatellite[]> => {
  return Satellite.find({ missionId })
    .sort({ launchDate: -1 })
    .skip(from)
    .limit(size)
    .exec();
};

export const countSatellitesByMissionIds = async (
  missionIds: number[]
): Promise<{ [key: string]: number }> => {
  const counts = await Satellite.aggregate([
    { $match: { missionId: { $in: missionIds } } },
    { $group: { _id: "$missionId", count: { $sum: 1 } } },
  ]).exec();

  return missionIds.reduce((acc, id) => {
    acc[id] =
      counts.find((count: { _id: number; count: number }) => count._id === id)
        ?.count || 0;
    return acc;
  }, {} as { [key: string]: number });
};

const validateMission = async (missionId: number) => {
  try {
    const response = await axios.get(`${MISSION_SERVICE_URL}/${missionId}`);
    if (!response.data) {
      throw new Error(`Mission with id ${missionId} doesn't exist`);
    }
  } catch (err) {
    throw new Error(`Mission with id ${missionId} doesn't exist`);
  }
};
