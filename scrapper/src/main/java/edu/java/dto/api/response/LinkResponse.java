package edu.java.dto.api.response;

import java.net.URI;
import lombok.Data;

@Data
public class LinkResponse {
    private Long id;
    private URI url;
}
