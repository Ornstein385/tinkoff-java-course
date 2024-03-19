package edu.java.dto.api.internal.request;

import java.net.URI;
import lombok.Data;

@Data
public class AddLinkRequest {
    private URI link;
}

