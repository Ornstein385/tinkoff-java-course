package edu.java.dto.api.external;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GitHubBranchesResponse(
    @JsonProperty("name") String name,
    @JsonProperty("commit") Commit commit,
    @JsonProperty("protected") boolean isProtected

) {
    public record Commit(
        @JsonProperty("sha") String sha,
        @JsonProperty("url") String url
    ) {
    }
}
