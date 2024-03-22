package edu.java.dto.api.internal.request;

import java.net.URI;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LinkUpdateRequest {
    private Long id;
    private URI url;
    private String description;
    private List<Long> tgChatIds;
}
