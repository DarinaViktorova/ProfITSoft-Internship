package my.space.spacemissions.controllers;

import my.space.spacemissions.dto.MissionFilterRequest;
import my.space.spacemissions.dto.MissionListResponse;
import my.space.spacemissions.dto.MissionUpdateRequest;
import my.space.spacemissions.entities.Mission;
import my.space.spacemissions.services.MissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/missions")
public class MissionController {

    @Autowired
    private MissionService missionService;

    @PostMapping
    public ResponseEntity<Mission> createMission(@RequestBody Mission mission) {
        Mission createdMission = missionService.createMission(mission);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMission);
    }

    @GetMapping
    public ResponseEntity<List<Mission>> getAllMissions() {
        List<Mission> missions = missionService.getAllMissions();
        return ResponseEntity.ok(missions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mission> getMissionById(@PathVariable Long id) {
        Mission mission = missionService.getMissionById(id);
        return ResponseEntity.ok(mission);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Mission> updateMission(@PathVariable Long id, @RequestBody MissionUpdateRequest updateRequest) {
        Mission updatedMission = missionService.updateMission(id, updateRequest);
        return ResponseEntity.ok(updatedMission);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMission(@PathVariable Long id) {
        missionService.deleteMission(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/_list")
    public ResponseEntity<MissionListResponse> getMissionsFiltered(@RequestBody MissionFilterRequest request) {
        Page<Mission> missionsPage = missionService.getMissionsFiltered(
                request.getPlanetName(), request.getMissionYear(), request.getPage(), request.getSize());

        MissionListResponse response = new MissionListResponse();
        response.setList(missionsPage.getContent());
        response.setTotalPages(missionsPage.getTotalPages());

        return ResponseEntity.ok(response);
    }
}
