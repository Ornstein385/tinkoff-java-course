package edu.java.client;

import edu.java.dto.GitHubRepoResponse;

public interface GitHubClient {
    GitHubRepoResponse fetchRepository(String owner, String repo);
}
