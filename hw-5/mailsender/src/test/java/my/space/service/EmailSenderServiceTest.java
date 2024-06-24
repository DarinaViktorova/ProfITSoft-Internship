package my.space.service;

import my.space.EmailSenderApplication;
import my.space.dto.MissionMessageReceived;
import my.space.model.Message;
import my.space.repository.EmailRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import my.space.service.config.ElasticSearchConfigTest;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {EmailSenderApplication.class, ElasticSearchConfigTest.class})
class EmailSenderServiceTest {

    @Autowired
    private EmailSenderService emailService;

    @MockBean
    private JavaMailSender mailSender;

    @MockBean
    private EmailRepository emailRepository;

    private Message msg;
    private MissionMessageReceived missionMessageReceived;

    @BeforeEach
    void setUp() {
        missionMessageReceived = MissionMessageReceived.builder()
                .id("1")
                .subject("Subject")
                .content("Content")
                .emailConsumer("EmailConsumer")
                .build();

        msg = Message.builder()
                .id(missionMessageReceived.getId())
                .emailConsumer(missionMessageReceived.getEmailConsumer())
                .content(missionMessageReceived.getContent())
                .subject(missionMessageReceived.getSubject())
                .retryCount(0)
                .status("Successfully")
                .build();
    }

    @Test
    void whenProcessEmailReceived_thenSuccess() {
        doNothing().when(mailSender).send(any(SimpleMailMessage.class));
        when(emailRepository.save(any(Message.class))).thenReturn(msg);

        emailService.processEmailReceived(missionMessageReceived);

        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
        verify(emailRepository, times(1)).save(any(Message.class));
    }

    @Test
    void whenProcessEmailReceived_thenFail() {
        Message failedMessage = Message.builder()
                .id("1")
                .status("Error")
                .subject("Test Subject")
                .content("Test Content")
                .retryCount(0)
                .emailConsumer("EmailConsumer")
                .build();

        when(emailRepository.findByStatus("Error")).thenReturn(Collections.singletonList(failedMessage));
        doThrow(new MailException("Test Exception") {}).when(mailSender).send(any(SimpleMailMessage.class));

        emailService.retryFailedEmails();

        verify(emailRepository, times(1)).save(failedMessage);
        Assertions.assertEquals(1, failedMessage.getRetryCount());
    }
}
