package my.space.spacemissions.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import my.space.spacemissions.dto.MissionFilterRequest;
import my.space.spacemissions.services.ReportService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@AutoConfigureMockMvc
public class ReportControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ReportService reportService;

    @Test
    public void testGenerateReport() throws Exception {
        MissionFilterRequest filterRequest = new MissionFilterRequest();
        filterRequest.setPlanetName("Mars");
        filterRequest.setMissionYear(2025);

        byte[] mockReportData = "ID,Planet Name,Mission Year\n1,Mars,2025\n2,Mars,2025\n".getBytes();

        given(reportService.generateReport(any(MissionFilterRequest.class))).willReturn(mockReportData);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/missions/_report")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON) // Устанавливаем заголовок Accept
                        .content(objectMapper.writeValueAsString(filterRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk()) // Проверяем, что статус ответа - ОК
                .andExpect(MockMvcResultMatchers.content().bytes(mockReportData)); // Проверяем, что содержимое ответа соответствует фиктивным данным отчета
    }
}
