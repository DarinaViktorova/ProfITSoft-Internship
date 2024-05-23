package my.space.spacemissions.repositories;

import my.space.spacemissions.entities.Spaceship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpaceshipRepository extends JpaRepository<Spaceship, Long> {
    boolean existsBySpaceshipName(String spaceshipName);
    Optional<Spaceship> findBySpaceshipName(String spaceshipName);
}
