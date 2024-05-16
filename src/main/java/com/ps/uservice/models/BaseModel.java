package com.ps.uservice.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@MappedSuperclass
@Getter
@Setter
public class BaseModel {
    @Id()
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false)
    private UUID id;
    @JsonIgnore
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
    }
}
