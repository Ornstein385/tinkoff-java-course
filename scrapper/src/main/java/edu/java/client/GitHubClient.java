package edu.java.client;

import edu.java.dto.api.external.GitHubRepoResponse;

public interface GitHubClient {
    GitHubRepoResponse fetchRepository(String owner, String repo);

}
