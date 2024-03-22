package edu.java.bot.dto.api.response;

import java.net.URI;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LinkResponse {
    private Long id;
    private URI url;
}
