package my.space.spacemissions.controllers;

import my.space.spacemissions.entities.Spaceship;
import my.space.spacemissions.services.SpaceshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/spaceship")
public class SpaceshipController {

    @Autowired
    private SpaceshipService spaceshipService;

    @GetMapping
    public ResponseEntity<List<Spaceship>> getAllSpaceships() {
        List<Spaceship> spaceships = spaceshipService.getAllSpaceships();
        return new ResponseEntity<>(spaceships, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Spaceship> createSpaceship(@RequestBody Spaceship spaceship) {
        Spaceship createdSpaceship = spaceshipService.createSpaceship(spaceship);
        return new ResponseEntity<>(createdSpaceship, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Spaceship> updateSpaceship(@PathVariable Long id, @RequestBody Spaceship spaceship) {
        Spaceship updatedSpaceship = spaceshipService.updateSpaceship(id, spaceship);
        return new ResponseEntity<>(updatedSpaceship, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSpaceship(@PathVariable Long id) {
        spaceshipService.deleteSpaceship(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

