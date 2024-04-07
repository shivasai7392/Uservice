package com.ps.uservice.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Calendar;
import java.util.Date;

@Getter
@Setter
@Entity(name = "sessions")
public class Session extends BaseModel{
    @Column(length = 4096)
    private String token;
    @Temporal(TemporalType.TIMESTAMP)
    private Date expiryingAt;
    @ManyToOne
    private User user;
    @Enumerated(EnumType.ORDINAL)
    private SessionStatus status;

    @PrePersist
    protected void setExpiryAt() {
        // Calculate expiryAt to be 5 minutes ahead of createdAt
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(super.getCreatedAt());
        calendar.add(Calendar.MINUTE, 5);
        this.expiryingAt = calendar.getTime();
    }
}
