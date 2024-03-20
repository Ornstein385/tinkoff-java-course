package edu.java.client;

import edu.java.dto.api.external.GitHubBranchesResponse;
import edu.java.dto.api.external.GitHubCommitResponse;
import edu.java.dto.api.external.GitHubPullsResponse;
import edu.java.dto.api.external.GitHubRepoResponse;

public interface GitHubClient {
    GitHubRepoResponse fetchRepository(String owner, String repo);

    GitHubBranchesResponse fetchBranches(String owner, String repo);

    GitHubCommitResponse fetchCommits(String owner, String repo, String commit);

    GitHubPullsResponse fetchPulls(String owner, String repo);
}
