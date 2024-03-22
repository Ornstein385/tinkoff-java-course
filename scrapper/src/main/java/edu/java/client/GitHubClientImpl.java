package edu.java.client;

import edu.java.dto.api.external.GitHubBranchesResponse;
import edu.java.dto.api.external.GitHubCommitResponse;
import edu.java.dto.api.external.GitHubPullsResponse;
import edu.java.dto.api.external.GitHubRepoResponse;
import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class GitHubClientImpl implements GitHubClient {

    private final WebClient webClient;

    public GitHubClientImpl(@Qualifier("githubWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public GitHubRepoResponse fetchRepository(String owner, String repo) {
        return webClient.get()
            .uri("/repos/{owner}/{repo}", owner, repo)
            .retrieve()
            .bodyToMono(GitHubRepoResponse.class).block();
    }

    @Override
    public List<GitHubBranchesResponse> fetchBranches(String owner, String repo) {
        return webClient.get()
            .uri("/repos/{owner}/{repo}/branches", owner, repo)
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<List<GitHubBranchesResponse>>() {
            }).block();
    }

    @Override
    public GitHubCommitResponse fetchCommits(String owner, String repo, String commit) {
        return webClient.get()
            .uri("/repos/{owner}/{repo}/commits/{commit}", owner, repo, commit)
            .retrieve()
            .bodyToMono(GitHubCommitResponse.class).block();
    }

    @Override
    public List<GitHubPullsResponse> fetchPulls(String owner, String repo) {
        return webClient.get()
            .uri("/repos/{owner}/{repo}/pulls", owner, repo)
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<List<GitHubPullsResponse>>() {
            }).block();
    }
}
