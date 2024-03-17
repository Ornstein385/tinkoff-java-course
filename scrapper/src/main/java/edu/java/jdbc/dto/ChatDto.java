package edu.java.jdbc.dto;

import java.time.OffsetDateTime;

public class ChatDto {
    private Long id;
    private OffsetDateTime createdAt;

    public ChatDto(Long id, OffsetDateTime createdAt) {
        this.id = id;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
