package todo.data;

import java.time.Instant;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Instant createTime;
    private Instant updateTime;
    private String text;
    @Enumerated(EnumType.STRING)
    private Status status;
    private Instant finishingTime;

    public Note() {}

    public Note(Long id,
        Instant createTime,
        Instant updateTime,
        String text,
        Status status,
        Instant finishingTime
    ) {
        this.id = id;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.text = text;
        this.status = status;
        this.finishingTime = finishingTime;
    }

    public Long getId() {
        return id;
    }

    public Instant getCreateTime() {
        return createTime;
    }
    
    public Instant getUpdateTime() {
        return updateTime;
    }

    public String getText() {
        return text;
    }

    public Status getStatus() {
        return status;
    }

    public Instant getFinishingTime() {
        return finishingTime;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setFinishingTime(Instant finishingTime) {
        this.finishingTime = finishingTime;
    }
}
