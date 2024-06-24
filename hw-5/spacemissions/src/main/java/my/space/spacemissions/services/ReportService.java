package my.space.spacemissions.services;

import my.space.spacemissions.dto.MissionFilterRequest;
import my.space.spacemissions.entities.Mission;
import my.space.spacemissions.repositories.MissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

@Service
public class ReportService {

    @Autowired
    private MissionRepository missionRepository;

    public byte[] generateReport(MissionFilterRequest filterRequest) {
        List<Mission> missions = missionRepository.findAll();

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             OutputStreamWriter writer = new OutputStreamWriter(outputStream)) {

            // Записати заголовки
            writer.write("ID,Planet Name,Mission Year\n");

            // Записати дані
            for (Mission mission : missions) {
                writer.write(String.format("%d,%s,%d\n",
                        mission.getId(), mission.getPlanetName(), mission.getMissionYear()));
            }

            writer.flush();
            return outputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Failed to generate report", e);
        }
    }
}
