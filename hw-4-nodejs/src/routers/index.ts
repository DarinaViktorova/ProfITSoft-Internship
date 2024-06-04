import express from 'express';
import ping from 'src/controllers/ping';
import satellitesRoutes from './satellites/index';

const router = express.Router();

router.get('/ping', ping);

router.use('/satellites', satellitesRoutes);

export default router;
