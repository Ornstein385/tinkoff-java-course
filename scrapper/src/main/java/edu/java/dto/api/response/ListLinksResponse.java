package edu.java.dto.api.response;

import java.util.List;
import lombok.Data;

@Data
public class ListLinksResponse {
    private List<LinkResponse> links;
    private Integer size;
}
