package my.space.spacemissions.services;

import my.space.spacemissions.dto.MissionUpdateRequest;
import my.space.spacemissions.entities.Mission;
import my.space.spacemissions.entities.Spaceship;
import my.space.spacemissions.repositories.MissionRepository;
import my.space.spacemissions.repositories.SpaceshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class MissionService {

    @Autowired
    private MissionRepository missionRepository;

    @Autowired
    private SpaceshipRepository spaceshipRepository;

    public Mission createMission(Mission mission) {
        return missionRepository.save(mission);
    }

    public List<Mission> getAllMissions() {
        return missionRepository.findAll();
    }

    public Mission getMissionById(Long id) {
        return missionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Mission not found with id: " + id));
    }

    public Mission updateMission(Long id, MissionUpdateRequest updateRequest) {
        Mission mission = missionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Mission not found with id: " + id));

        mission.setPlanetName(updateRequest.getPlanetName());
        mission.setMissionYear(updateRequest.getMissionYear());

        mission.getSpaceships().clear();

        List<Spaceship> updatedSpaceships = new ArrayList<>();
        for (Spaceship spaceshipDTO : updateRequest.getSpaceships()) {
            Spaceship spaceship = new Spaceship();
            spaceship.setSpaceshipName(spaceshipDTO.getSpaceshipName());
            spaceship.setDestinationPlanet(spaceshipDTO.getDestinationPlanet());
            spaceship.setCapacity(spaceshipDTO.getCapacity());
            spaceship.setMission(mission);
            updatedSpaceships.add(spaceship);
        }

        mission.getSpaceships().addAll(updatedSpaceships);

        return missionRepository.save(mission);
    }


    public void deleteMission(Long id) {
        Mission existingMission = missionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Mission not found with id: " + id));

        missionRepository.delete(existingMission);
    }

    public Page<Mission> getMissionsFiltered(String planetName, Integer missionYear, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return missionRepository.findAllByPlanetNameContainingAndMissionYear(planetName, missionYear, pageable);
    }

    public void processUpload(Mission[] missions) {
        List<Mission> validMissions = new ArrayList<>();
        for (Mission mission : missions) {
            validMissions.add(mission);
        }
        missionRepository.saveAll(validMissions);
    }
}
