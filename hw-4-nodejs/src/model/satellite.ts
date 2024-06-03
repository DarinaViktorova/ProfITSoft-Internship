import mongoose, { Document, Schema } from 'mongoose';

export interface ISatellite extends Document {
  name: string;
  missionId: number;
  launchDate: Date;
}

const SatelliteSchema: Schema = new Schema({
  name: { type: String, required: true },
  missionId: { type: Number, required: true },
  launchDate: { type: Date, default: Date.now },
});

export default mongoose.model<ISatellite>('Satellite', SatelliteSchema);
