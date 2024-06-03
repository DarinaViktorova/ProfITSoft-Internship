import express from 'express';
import {
  create,
  listByMissionId,
  countByMissionIds } from '../../controllers/satellite/index';

const router = express.Router();

router.post('', create);
router.get('', listByMissionId);
router.post('/_counts', countByMissionIds);

export default router;
