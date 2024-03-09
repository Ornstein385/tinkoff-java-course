package edu.java.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;

public record GitHubRepoResponse(
    @JsonProperty("id") long id,
    @JsonProperty("node_id") String nodeId,
    @JsonProperty("name") String name,
    @JsonProperty("private") boolean isPrivate,
    @JsonProperty("owner") Owner owner,
    @JsonProperty("url") String url,
    @JsonProperty("created_at") OffsetDateTime createdAt,
    @JsonProperty("updated_at") OffsetDateTime updatedAt,
    @JsonProperty("pushed_at") OffsetDateTime pushedAt
) {

    public record Owner(
        @JsonProperty("login") String login,
        @JsonProperty("id") long id,
        @JsonProperty("node_id") String nodeId
    ) {
    }
}
