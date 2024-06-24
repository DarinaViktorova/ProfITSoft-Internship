package my.space.spacemissions.services;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import my.space.spacemissions.entities.Spaceship;
import my.space.spacemissions.repositories.SpaceshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class SpaceshipService {

    @Autowired
    private SpaceshipRepository spaceshipRepository;

    public List<Spaceship> getAllSpaceships() {
        return spaceshipRepository.findAll();
    }

    public Spaceship createSpaceship(Spaceship spaceship) {
        // Перевірка на унікальність
        if (spaceshipRepository.existsBySpaceshipName(spaceship.getSpaceshipName())) {
            throw new IllegalArgumentException("Spaceship with this name already exists.");
        }
        return spaceshipRepository.save(spaceship);
    }

    public Spaceship updateSpaceship(Long id, Spaceship spaceship) {
        // Перевірка чи існує корабель з таким ID
        Spaceship existingSpaceship = spaceshipRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Spaceship not found with id: " + id));

        // Перевірка унікальності нового найменування
        if (!existingSpaceship.getSpaceshipName().equals(spaceship.getSpaceshipName()) &&
                spaceshipRepository.existsBySpaceshipName(spaceship.getSpaceshipName())) {
            throw new IllegalArgumentException("Spaceship with this name already exists.");
        }

        // Оновлення і збереження корабля
        existingSpaceship.setSpaceshipName(spaceship.getSpaceshipName());
        existingSpaceship.setDestinationPlanet(spaceship.getDestinationPlanet());
        existingSpaceship.setCapacity(spaceship.getCapacity());
        return spaceshipRepository.save(existingSpaceship);
    }

    public void deleteSpaceship(Long id) {
        Spaceship spaceship = spaceshipRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Spaceship not found with id: " + id));
        spaceshipRepository.delete(spaceship);
    }
}
