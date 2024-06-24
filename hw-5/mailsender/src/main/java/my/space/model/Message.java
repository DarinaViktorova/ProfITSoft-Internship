package my.space.model;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Document(indexName="messages")
public class Message {
    @Id
    private String id;

    @Field(type = FieldType.Text)
    private String subject;

    @Field(type = FieldType.Text)
    private String content;

    @Field(type = FieldType.Keyword)
    private String emailConsumer;

    @Field(type = FieldType.Text)
    private String status;

    @Field(type = FieldType.Text)
    private String errorMsg;

    @Field(type = FieldType.Integer)
    private Integer retryCount;

    @Field(type = FieldType.Text)
    private String lastAttemptTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getEmailConsumer() {
        return emailConsumer;
    }

    public void setEmailConsumer(String emailConsumer) {
        this.emailConsumer = emailConsumer;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Integer getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(Integer retryCount) {
        this.retryCount = retryCount;
    }

    public String getLastAttemptTime() {
        return lastAttemptTime;
    }

    public void setLastAttemptTime(String lastAttemptTime) {
        this.lastAttemptTime = lastAttemptTime;
    }
}
