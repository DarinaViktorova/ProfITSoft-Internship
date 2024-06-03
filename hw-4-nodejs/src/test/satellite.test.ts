import { expect } from 'chai';
import request from 'supertest';
import mongoose from 'mongoose';
import createApp from '../app'; 
import express from 'express';

const satellitePayload = {
  missionId: 1, 
  name: "Test Satellite",
  launchDate: new Date().toISOString(),
};

describe('Satellite API', () => {
  let app: express.Application;

  before(async () => {
    app = await createApp();
    await mongoose.connect('mongodb://127.0.0.1:27017/testdb');
  });

  after(async () => {
    await mongoose.connection.db.dropDatabase();
    await mongoose.connection.close();
  });

  it('should create a new satellite', async () => {
    const res = await request(app)
      .post('/satellites')
      .send(satellitePayload);

    expect(res.status).to.equal(201);
    expect(res.body).to.have.property('id');
  });

  it('should list satellites by mission ID', async () => {
    const res = await request(app)
      .get('/satellites')
      .query({ entity1Id: 1, size: 10, from: 0 });

    expect(res.status).to.equal(200);
    expect(res.body).to.be.an('array');
  });

  it('should count satellites by mission IDs', async () => {
    const res = await request(app)
      .post('/satellites/_counts')
      .send({ entity1Ids: [1] });

    expect(res.status).to.equal(200);
    expect(res.body).to.have.property('1');
    expect(res.body[1]).to.be.at.least(0);
  });
});
