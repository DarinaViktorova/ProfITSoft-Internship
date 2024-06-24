package my.space.spacemissions.services;

import jakarta.persistence.EntityNotFoundException;
import my.space.spacemissions.entities.Spaceship;
import my.space.spacemissions.repositories.SpaceshipRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SpaceshipServiceTest {
    @Mock
    private SpaceshipRepository spaceshipRepository;

    @InjectMocks
    private SpaceshipService spaceshipService;

    @Test
    public void testGetAllSpaceships() {
        Spaceship spaceship1 = new Spaceship("Ship1", "Planet1", 100);
        Spaceship spaceship2 = new Spaceship("Ship2", "Planet2", 200);
        List<Spaceship> spaceships = List.of(spaceship1, spaceship2);

        when(spaceshipRepository.findAll()).thenReturn(spaceships);

        List<Spaceship> result = spaceshipService.getAllSpaceships();

        assertEquals(spaceships, result);
    }

    @Test
    public void testCreateSpaceship() {
        Spaceship spaceship = new Spaceship("NewShip", "NewPlanet", 150);

        when(spaceshipRepository.existsBySpaceshipName(spaceship.getSpaceshipName())).thenReturn(false);
        when(spaceshipRepository.save(spaceship)).thenReturn(spaceship);

        Spaceship result = spaceshipService.createSpaceship(spaceship);

        assertEquals(spaceship, result);
        verify(spaceshipRepository, times(1)).save(spaceship);
    }

    @Test
    public void testUpdateSpaceship() {
        long id = 1L;
        Spaceship existingSpaceship = new Spaceship("ExistingShip", "ExistingPlanet", 200);
        Spaceship updatedSpaceship = new Spaceship("UpdatedShip", "UpdatedPlanet", 250);

        when(spaceshipRepository.findById(id)).thenReturn(Optional.of(existingSpaceship));
        when(spaceshipRepository.existsBySpaceshipName(updatedSpaceship.getSpaceshipName())).thenReturn(false);
        when(spaceshipRepository.save(existingSpaceship)).thenReturn(updatedSpaceship);

        Spaceship result = spaceshipService.updateSpaceship(id, updatedSpaceship);

        assertEquals(updatedSpaceship, result);
        verify(spaceshipRepository, times(1)).save(existingSpaceship);
    }

    @Test
    public void testDeleteSpaceship() {
        long id = 1L;
        Spaceship spaceship = new Spaceship("ToDelete", "Planet", 150);

        when(spaceshipRepository.findById(id)).thenReturn(Optional.of(spaceship));

        spaceshipService.deleteSpaceship(id);

        verify(spaceshipRepository, times(1)).delete(spaceship);
    }

    @Test
    public void testCreateSpaceshipWithExistingName() {
        Spaceship spaceship = new Spaceship("ExistingShip", "ExistingPlanet", 200);

        when(spaceshipRepository.existsBySpaceshipName(spaceship.getSpaceshipName())).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> spaceshipService.createSpaceship(spaceship));
        verify(spaceshipRepository, never()).save(spaceship);
    }

    @Test
    public void testUpdateSpaceshipWithExistingName() {

        long id = 1L;
        Spaceship existingSpaceship = new Spaceship("ExistingShip", "ExistingPlanet", 200);
        Spaceship updatedSpaceship = new Spaceship("UpdatedShip", "UpdatedPlanet", 250);

        when(spaceshipRepository.findById(id)).thenReturn(Optional.of(existingSpaceship));
        when(spaceshipRepository.existsBySpaceshipName(updatedSpaceship.getSpaceshipName())).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> spaceshipService.updateSpaceship(id, updatedSpaceship));
        verify(spaceshipRepository, never()).save(existingSpaceship);
    }

    @Test
    public void testGetAllSpaceshipsEmptyList() {
        when(spaceshipRepository.findAll()).thenReturn(List.of());

        List<Spaceship> result = spaceshipService.getAllSpaceships();

        assertTrue(result.isEmpty());
    }
}