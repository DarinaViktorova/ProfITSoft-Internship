package my.space.spacemissions.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import my.space.spacemissions.entities.Spaceship;
import my.space.spacemissions.services.SpaceshipService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class SpaceshipControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SpaceshipService spaceshipService;

    @BeforeEach
    public void setup() {
    }

    @Test
    public void testGetAllSpaceships() throws Exception {
        Spaceship spaceship = new Spaceship();
        spaceship.setId(1L);
        spaceship.setSpaceshipName("Starship");
        spaceship.setDestinationPlanet("Mars");
        spaceship.setCapacity(100);

        List<Spaceship> spaceships = Collections.singletonList(spaceship);

        given(spaceshipService.getAllSpaceships()).willReturn(spaceships);

        mockMvc.perform(get("/api/spaceship"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].spaceshipName").value("Starship"))
                .andExpect(jsonPath("$[0].destinationPlanet").value("Mars"))
                .andExpect(jsonPath("$[0].capacity").value(100));
    }

    @Test
    public void testCreateSpaceship() throws Exception {
        Spaceship spaceship = new Spaceship();
        spaceship.setSpaceshipName("Voyager");
        spaceship.setDestinationPlanet("Jupiter");
        spaceship.setCapacity(50);

        Spaceship createdSpaceship = new Spaceship();
        createdSpaceship.setId(1L);
        createdSpaceship.setSpaceshipName("Voyager");
        createdSpaceship.setDestinationPlanet("Jupiter");
        createdSpaceship.setCapacity(50);

        given(spaceshipService.createSpaceship(any(Spaceship.class))).willReturn(createdSpaceship);

        mockMvc.perform(post("/api/spaceship")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(spaceship)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.spaceshipName").value("Voyager"))
                .andExpect(jsonPath("$.destinationPlanet").value("Jupiter"))
                .andExpect(jsonPath("$.capacity").value(50));
    }

    @Test
    public void testUpdateSpaceship() throws Exception {
        Long id = 1L;
        Spaceship spaceship = new Spaceship();
        spaceship.setSpaceshipName("Enterprise");
        spaceship.setDestinationPlanet("Venus");
        spaceship.setCapacity(200);

        Spaceship updatedSpaceship = new Spaceship();
        updatedSpaceship.setId(id);
        updatedSpaceship.setSpaceshipName("Enterprise");
        updatedSpaceship.setDestinationPlanet("Venus");
        updatedSpaceship.setCapacity(200);

        given(spaceshipService.updateSpaceship(eq(id), any(Spaceship.class))).willReturn(updatedSpaceship);

        mockMvc.perform(put("/api/spaceship/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(spaceship)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.spaceshipName").value("Enterprise"))
                .andExpect(jsonPath("$.destinationPlanet").value("Venus"))
                .andExpect(jsonPath("$.capacity").value(200));
    }

    @Test
    public void testDeleteSpaceship() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete("/api/spaceship/{id}", id))
                .andExpect(status().isNoContent());
    }
}
