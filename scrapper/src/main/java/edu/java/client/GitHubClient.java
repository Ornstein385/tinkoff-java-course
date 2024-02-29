package edu.java.client;

import edu.java.dto.GitHubRepoResponse;
import reactor.core.publisher.Mono;

public interface GitHubClient {
    Mono<GitHubRepoResponse> fetchRepository(String owner, String repo);
}
