package my.space.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class MissionMessageReceived {

    private String id;
    private String subject;
    private String content;
    private String emailConsumer;

}
