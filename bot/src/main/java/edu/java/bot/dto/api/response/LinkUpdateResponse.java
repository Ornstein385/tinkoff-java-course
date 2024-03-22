package edu.java.bot.dto.api.response;

import java.net.URI;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LinkUpdateResponse {
    private Long id;
    private URI url;
    private String description;
    private List<Long> tgChatIds;
}
