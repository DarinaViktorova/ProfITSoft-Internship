package my.space.service;


import my.space.dto.MissionMessageReceived;

public interface EmailService {
    void processEmailReceived(MissionMessageReceived message);
}
