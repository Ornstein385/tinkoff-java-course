package edu.java.dto.api.internal.request;

import java.net.URI;
import java.util.List;
import lombok.Data;

@Data
public class LinkUpdateRequest {
    private Long id;
    private URI url;
    private String description;
    private List<Long> tgChatIds;

    public LinkUpdateRequest(Long id, URI url, String description, List<Long> tgChatIds) {
        this.id = id;
        this.url = url;
        this.description = description;
        this.tgChatIds = tgChatIds;
    }
}
