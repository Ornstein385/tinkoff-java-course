package edu.java.dto.api.external;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;

public record GitHubPullsResponse(
    @JsonProperty("url") String url,
    @JsonProperty("id") long id,
    @JsonProperty("node_id") String nodeId,
    @JsonProperty("created_at") OffsetDateTime createdAt,
    @JsonProperty("updated_at") OffsetDateTime updatedAt,
    @JsonProperty("closed_at") OffsetDateTime closedAt,
    @JsonProperty("merged_at") OffsetDateTime mergedAt) {
}
