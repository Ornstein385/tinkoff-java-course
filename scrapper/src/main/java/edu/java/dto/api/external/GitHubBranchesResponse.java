package edu.java.dto.api.external;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record GitHubBranchesResponse(
    List<Branch> branches
) {

    public record Branch(
        @JsonProperty("name") String name,
        @JsonProperty("commit") Commit commit
    ) {

        public record Commit(
            @JsonProperty("sha") String sha,
            @JsonProperty("url") String url
        ) {
        }
    }
}
