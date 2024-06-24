package my.space.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import my.space.dto.MissionMessageReceived;
import my.space.model.Message;
import my.space.repository.EmailRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailSenderService implements EmailService {

    private final JavaMailSender mailSender;

    private final EmailRepository emailRepository;

    @Value("${spring.mail.email}")
    private String emailAdmin;

    @Value("${email.sender}")
    private String fakeEmailSender;



    @Override
    public void processEmailReceived(MissionMessageReceived msg) {

        Message message = Message.builder()
                                 .id(msg.getId())
                                 .subject(msg.getSubject())
                                 .content(msg.getContent())
                                 .emailConsumer(msg.getEmailConsumer())
                                 .status("Success")
                                 .retryCount(0)
                                 .build();

        sendMessageAndUpdateStatus(message);
    }

    @Scheduled(fixedRate = 300000)
    public void retryFailedEmails() {
        List<Message> failedMessages = emailRepository.findByStatus("Error");
        failedMessages.forEach(this::sendMessageAndUpdateStatus);
    }

    private void sendMessageAndUpdateStatus(Message msg) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(fakeEmailSender);
        message.setTo(emailAdmin);
        message.setSubject(msg.getSubject());
        message.setText(msg.getContent());

        try {
            msg.setStatus("Success");
            msg.setErrorMsg(null);
            mailSender.send(message);
        } catch (MailException ex) {
            msg.setStatus("Error");
            msg.setErrorMsg(ex.getClass().getName() + ": " + ex.getMessage());
            msg.setRetryCount(msg.getRetryCount() + 1);
            msg.setLastAttemptTime(LocalDateTime.now().toString());
        }

        emailRepository.save(msg);
    }
}
