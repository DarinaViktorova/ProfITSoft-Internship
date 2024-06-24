package my.space.spacemissions.controllers;

import my.space.spacemissions.dto.UploadResponse;
import my.space.spacemissions.entities.Mission;
import my.space.spacemissions.services.MissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/missions")
public class UploadController {

    @Autowired
    private MissionService missionService;


    @PostMapping("/upload")
    public ResponseEntity<UploadResponse> upload(@RequestBody Mission[] data) {
        missionService.processUpload(data);
        UploadResponse response = new UploadResponse();
        response.setMessage("Upload successful");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
