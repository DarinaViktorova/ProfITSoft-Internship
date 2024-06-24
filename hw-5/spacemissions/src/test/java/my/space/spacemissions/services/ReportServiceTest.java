package my.space.spacemissions.services;

import my.space.spacemissions.dto.MissionFilterRequest;
import my.space.spacemissions.entities.Mission;
import my.space.spacemissions.repositories.MissionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReportServiceTest {

    @Mock
    private MissionRepository missionRepository;

    @InjectMocks
    private ReportService reportService;

    @Test
    public void testGenerateReport() throws Exception {
        // Создаем тестовые миссии
        Mission mission1 = new Mission();
        mission1.setId(1L);
        mission1.setPlanetName("Mars");
        mission1.setMissionYear(2025);

        Mission mission2 = new Mission();
        mission2.setId(2L);
        mission2.setPlanetName("Venus");
        mission2.setMissionYear(2024);

        List<Mission> missions = Arrays.asList(mission1, mission2);

        // Настройка mock объекта
        when(missionRepository.findAll()).thenReturn(missions);

        // Создаем фильтр для запроса отчета
        MissionFilterRequest filterRequest = new MissionFilterRequest();

        // Вызываем метод, который тестируем
        byte[] reportData = reportService.generateReport(filterRequest);

        // Ожидаемые данные отчета в формате CSV
        String expectedReport = "ID,Planet Name,Mission Year\n" +
                "1,Mars,2025\n" +
                "2,Venus,2024\n";

        // Проверяем, что результат отчета соответствует ожидаемым данным
        assertArrayEquals(expectedReport.getBytes(), reportData);
    }

    @Test
    public void testGenerateReportNoMissions() throws Exception {
        // Убеждаемся, что метод работает корректно, когда список миссий пуст
        // Настройка mock объекта
        when(missionRepository.findAll()).thenReturn(List.of());

        // Создаем фильтр для запроса отчета
        MissionFilterRequest filterRequest = new MissionFilterRequest();

        // Вызываем метод, который тестируем
        byte[] reportData = reportService.generateReport(filterRequest);

        // Ожидаемые данные отчета, когда нет миссий
        String expectedReport = "ID,Planet Name,Mission Year\n";

        // Проверяем, что результат отчета соответствует ожидаемым данным
        assertArrayEquals(expectedReport.getBytes(), reportData);
    }
}
