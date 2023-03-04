package domain;

import java.time.LocalDateTime;

public class MessageDTO {
    private String from;
    private String text;
    private LocalDateTime date;

    public MessageDTO(String from, String text, LocalDateTime date) {
        this.from = from;
        this.text = text;
        this.date = date;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
