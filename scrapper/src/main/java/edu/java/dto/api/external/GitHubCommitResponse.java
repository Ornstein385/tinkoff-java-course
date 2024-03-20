package edu.java.dto.api.external;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;

public record GitHubCommitResponse(
    @JsonProperty("sha") String sha,
    @JsonProperty("node_id") String nodeId,
    @JsonProperty("commit") CommitDetail commit,
    @JsonProperty("url") String url,
    @JsonProperty("html_url") String htmlUrl,
    @JsonProperty("comments_url") String commentsUrl
) {
    public record CommitDetail(
        @JsonProperty("author") ContributorDetail author,
        @JsonProperty("committer") ContributorDetail committer,
        @JsonProperty("message") String message,
        @JsonProperty("url") String url

    ) {
    }

    public record ContributorDetail(
        @JsonProperty("name") String name,
        @JsonProperty("email") String email,
        @JsonProperty("date") OffsetDateTime date
    ) {
    }

}
