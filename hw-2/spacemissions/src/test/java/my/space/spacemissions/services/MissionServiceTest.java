package my.space.spacemissions.services;

import my.space.spacemissions.dto.MissionUpdateRequest;
import my.space.spacemissions.entities.Mission;
import my.space.spacemissions.entities.Spaceship;
import my.space.spacemissions.repositories.MissionRepository;
import my.space.spacemissions.repositories.SpaceshipRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class MissionServiceTest {

    @Mock
    private MissionRepository missionRepository;

    @Mock
    private SpaceshipRepository spaceshipRepository;

    @InjectMocks
    private MissionService missionService;

    @Test
    void createMission() {
        // Arrange
        Mission mission = new Mission("Mars", 2025);

        // Stubbing the repository method
        when(missionRepository.save(mission)).thenReturn(mission);

        // Act
        Mission createdMission = missionService.createMission(mission);

        // Assert
        assertNotNull(createdMission);
        assertEquals("Mars", createdMission.getPlanetName());
        assertEquals(2025, createdMission.getMissionYear());

        // Verify that repository method was called once
        verify(missionRepository, times(1)).save(mission);
    }

    @Test
    void updateMission() {
        // Создаем тестовые данные
        Long missionId = 1L;
        MissionUpdateRequest updateRequest = new MissionUpdateRequest();
        updateRequest.setPlanetName("Mars");
        updateRequest.setMissionYear(2025);

        // Создаем список кораблей
        List<Spaceship> spaceships = new ArrayList<>();
        Spaceship spaceship = new Spaceship("Red Rocket", "Jupiter", 100);
        spaceships.add(spaceship);
        updateRequest.setSpaceships(spaceships);

        Mission existingMission = new Mission("Earth", 2022);
        existingMission.setId(missionId);

        // Настройка поведения mock объектов
        when(missionRepository.findById(missionId)).thenReturn(Optional.of(existingMission));
        when(missionRepository.save(any(Mission.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Выполняем метод, который тестируем
        Mission updatedMission = missionService.updateMission(missionId, updateRequest);

        // Проверяем, что метод findById вызван один раз с аргументом missionId
        verify(missionRepository, times(1)).findById(missionId);

        // Проверяем, что метод save вызван один раз и что ему был передан обновленный объект Mission
        verify(missionRepository, times(1)).save(any(Mission.class));

        // Проверяем, что метод возвращает ожидаемый обновленный объект Mission
        assertEquals(updateRequest.getPlanetName(), updatedMission.getPlanetName());
        assertEquals(updateRequest.getMissionYear(), updatedMission.getMissionYear());

        // Проверяем, что у объекта Mission установлены правильные данные для Spaceship
        Spaceship updatedSpaceship = updatedMission.getSpaceships().get(0);
        assertEquals("Red Rocket", updatedSpaceship.getSpaceshipName());
        assertEquals("Jupiter", updatedSpaceship.getDestinationPlanet());
        assertEquals(100, updatedSpaceship.getCapacity());
    }


    @Test
    void getMissionById() {
        // Arrange
        Long missionId = 1L;
        Mission mission = new Mission("Mars", 2025);
        mission.setId(missionId);

        // Stubbing the repository method
        when(missionRepository.findById(missionId)).thenReturn(Optional.of(mission));

        // Act
        Mission foundMission = missionService.getMissionById(missionId);

        // Assert
        assertNotNull(foundMission);
        assertEquals("Mars", foundMission.getPlanetName());
        assertEquals(2025, foundMission.getMissionYear());

        // Verify that repository method was called once
        verify(missionRepository, times(1)).findById(missionId);
    }

    @Test
    void deleteMission() {
        // Arrange
        Long missionId = 1L;
        Mission mission = new Mission("Mars", 2025);
        mission.setId(missionId);

        // Stubbing the repository method
        when(missionRepository.findById(missionId)).thenReturn(Optional.of(mission));

        // Act
        missionService.deleteMission(missionId);

        // Verify that repository method was called once
        verify(missionRepository, times(1)).delete(mission);
    }
}
