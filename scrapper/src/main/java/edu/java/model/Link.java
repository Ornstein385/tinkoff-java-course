package edu.java.model;

import java.net.URI;
import java.time.OffsetDateTime;
import lombok.Data;

@Data
public class Link {
    private URI url;
    private OffsetDateTime lastUpdated;
}
