package my.space.spacemissions.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import my.space.spacemissions.entities.Mission;
import my.space.spacemissions.services.MissionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.doNothing;

@SpringBootTest
@AutoConfigureMockMvc
public class UploadControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MissionService missionService;

    @Test
    public void testUpload() throws Exception {
        Mission[] testData = {new Mission("Mars", 2025), new Mission("Venus", 2030)};

        doNothing().when(missionService).processUpload(testData);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/missions/upload")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testData)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Upload successful"));
    }
}
