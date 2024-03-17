package edu.java.jdbc.dto;

import java.time.OffsetDateTime;

public class LinkDto {
    private Long id;
    private String url;
    private OffsetDateTime lastUpdated;

    public LinkDto(Long id, String url, OffsetDateTime lastUpdated) {
    }

    public Long getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public OffsetDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setLastUpdated(OffsetDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
