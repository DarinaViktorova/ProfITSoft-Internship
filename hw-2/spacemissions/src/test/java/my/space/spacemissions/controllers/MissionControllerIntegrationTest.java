package my.space.spacemissions.controllers;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import com.fasterxml.jackson.databind.ObjectMapper;
import my.space.spacemissions.controllers.MissionController;
import my.space.spacemissions.dto.MissionUpdateRequest;
import my.space.spacemissions.entities.Mission;
import my.space.spacemissions.entities.Spaceship;
import my.space.spacemissions.repositories.MissionRepository;
import my.space.spacemissions.repositories.SpaceshipRepository;
import my.space.spacemissions.services.MissionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class MissionControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MissionRepository missionRepository;

    @Autowired
    private SpaceshipRepository spaceshipRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MissionService missionService;


    @BeforeEach
    public void setup() {
        missionRepository.deleteAll();
    }

    @Test
    public void testCreateMission() throws Exception {
        Mission mission = new Mission();
        mission.setPlanetName("Mars");
        mission.setMissionYear(2025);

        // Настройка mock-объекта MissionService
        given(missionService.createMission(any(Mission.class))).willReturn(mission);

        String missionJson = objectMapper.writeValueAsString(mission);

        mockMvc.perform(post("/api/missions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(missionJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.planetName").value("Mars"))
                .andExpect(jsonPath("$.missionYear").value(2025));
    }

    @Test
    public void testGetAllMissions() throws Exception {
        Mission mission = new Mission();
        mission.setPlanetName("Mars");
        mission.setMissionYear(2025);

        List<Mission> missions = Collections.singletonList(mission);

        // Настройка mock-объекта MissionService
        given(missionService.getAllMissions()).willReturn(missions);

        mockMvc.perform(get("/api/missions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].planetName").value("Mars"))
                .andExpect(jsonPath("$[0].missionYear").value(2025));
    }

    @Test
    public void testGetMissionById() throws Exception {
        Long missionId = 1L;
        Mission mission = new Mission();
        mission.setId(missionId);
        mission.setPlanetName("Mars");
        mission.setMissionYear(2025);

        given(missionService.getMissionById(missionId)).willReturn(mission);

        mockMvc.perform(get("/api/missions/" + missionId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(missionId))
                .andExpect(jsonPath("$.planetName").value("Mars"))
                .andExpect(jsonPath("$.missionYear").value(2025));
    }

    @Test
    public void testUpdateMission() throws Exception {
        Long missionId = 1L;
        MissionUpdateRequest updateRequest = new MissionUpdateRequest();
        updateRequest.setPlanetName("Mars");
        updateRequest.setMissionYear(2025);

        Mission updatedMission = new Mission();
        updatedMission.setId(missionId);
        updatedMission.setPlanetName("Mars");
        updatedMission.setMissionYear(2025);

        given(missionService.updateMission(eq(missionId), any(MissionUpdateRequest.class)))
                .willReturn(updatedMission);

        mockMvc.perform(put("/api/missions/" + missionId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(updatedMission.getId()))
                .andExpect(jsonPath("$.planetName").value(updatedMission.getPlanetName()))
                .andExpect(jsonPath("$.missionYear").value(updatedMission.getMissionYear()));
    }

    @Test
    public void testDeleteMission() throws Exception {
        Mission mission = new Mission();
        mission.setPlanetName("Mars");
        mission.setMissionYear(2025);
        Mission savedMission = missionRepository.save(mission);

        mockMvc.perform(delete("/api/missions/" + savedMission.getId()))
                .andExpect(status().isNoContent());
    }
}
