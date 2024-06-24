package my.space.spacemissions.controllers;

import my.space.spacemissions.dto.MissionFilterRequest;
import my.space.spacemissions.services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/missions")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @PostMapping("/_report")
    public ResponseEntity<byte[]> generateReport(@RequestBody MissionFilterRequest filterRequest) {
        byte[] reportData = reportService.generateReport(filterRequest);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        headers.setContentDispositionFormData("attachment", "report.csv");

        return new ResponseEntity<>(reportData, headers, HttpStatus.OK);
    }
}
