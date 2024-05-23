package my.space.spacemissions.repositories;

import my.space.spacemissions.entities.Mission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MissionRepository extends JpaRepository<Mission, Long> {
    Page<Mission> findAllByPlanetNameContainingAndMissionYear(String planetName, Integer missionYear, Pageable pageable);
}
