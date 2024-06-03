import { Request, Response } from "express";
import {
  createSatellite,
  listSatellitesByMissionId,
  countSatellitesByMissionIds,
} from "../../services/satellite/index";

export const create = async (req: Request, res: Response) => {
  try {
    const id = await createSatellite(req.body);
    res.status(201).send({ id });
  } catch (err: unknown) {
    if (err instanceof Error) {
      res.status(400).send({ message: err.message });
    } else {
      res.status(400).send({ message: 'Unknown error' });
    }
  }
};

export const listByMissionId = async (req: Request, res: Response) => {
  try {
    const { entity1Id, size = 10, from = 0 } = req.query;
    const satellites = await listSatellitesByMissionId(Number(entity1Id), +size, +from);
    res.send(satellites);
  } catch (err: unknown) {
    if (err instanceof Error) {
      res.status(400).send({ message: err.message });
    } else {
      res.status(400).send({ message: 'Unknown error' });
    }
  }
};

export const countByMissionIds = async (req: Request, res: Response) => {
  try {
    const { entity1Ids } = req.body;
    const counts = await countSatellitesByMissionIds(entity1Ids.map(Number));
    res.send(counts);
  } catch (err: unknown) {
    if (err instanceof Error) {
      res.status(400).send({ message: err.message });
    } else {
      res.status(400).send({ message: 'Unknown error' });
    }
  }
};
