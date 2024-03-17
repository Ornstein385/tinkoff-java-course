package edu.java.bot.dto.api.request;

import java.net.URI;
import lombok.Data;

@Data
public class AddLinkRequest {
    private URI link;
}

