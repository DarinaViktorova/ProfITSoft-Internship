package my.space.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import my.space.dto.MissionMessageReceived;
import my.space.service.EmailService;

@Component
@RequiredArgsConstructor
public class MissionListenerReceived {
    private final EmailService emailService;

    @KafkaListener(topics = "${kafka.topic.missionReceived}")
    public void missionReceived(MissionMessageReceived message) {
        emailService.processEmailReceived(message);
    }
}
