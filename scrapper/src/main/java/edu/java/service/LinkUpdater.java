package edu.java.service;

import edu.java.client.BotClient;
import edu.java.client.GitHubClient;
import edu.java.client.StackOverflowClient;
import edu.java.configuration.ApplicationConfig;
import edu.java.dto.api.external.GitHubBranchesResponse;
import edu.java.dto.api.external.GitHubCommitResponse;
import edu.java.dto.api.external.GitHubPullsResponse;
import edu.java.dto.api.internal.request.LinkUpdateRequest;
import edu.java.model.Link;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class LinkUpdater {
    StackOverflowClient stackOverflowClient;
    GitHubClient gitHubClient;
    BotClient botClient;
    LinkService linkService;
    ApplicationConfig applicationConfig;

    public LinkUpdater(
        StackOverflowClient stackOverflowClient,
        GitHubClient gitHubClient,
        BotClient botClient,
        LinkService linkService,
        ApplicationConfig applicationConfig
    ) {
        this.stackOverflowClient = stackOverflowClient;
        this.gitHubClient = gitHubClient;
        this.botClient = botClient;
        this.linkService = linkService;
        this.applicationConfig = applicationConfig;
    }

    public void update() {
        List<Link> linkList;
        do {
            linkList = (List<Link>) linkService.listAllLinks(
                applicationConfig.scheduler().interval().toMillis(),
                applicationConfig.database().maxResultLimit()
            ); //грузим ссылки частями
            for (Link link : linkList) {
                if (LinkTypeDeterminant.isGitHubCorrectLink(link.getUrl())) {
                    updateGitHub(link);
                } else if (LinkTypeDeterminant.isStackOverflowCorrectLink(link.getUrl())) {
                    updateStackOverflow(link);
                } else {
                    throw new RuntimeException("неизвестный тип ссылки");
                }
                linkService.refreshLinkUpdateTime(link.getUrl());
            }
        } while (!linkList.isEmpty());
    }

    private void updateGitHub(Link link) {
        String repo = LinkTypeDeterminant.getGitHubRepo(link.getUrl());
        String owner = LinkTypeDeterminant.getGitHubOwner(link.getUrl());

        GitHubBranchesResponse gitHubBranchesResponse = gitHubClient.fetchBranches(owner, repo);
        for (GitHubBranchesResponse.Branch branch : gitHubBranchesResponse.branches()) {
            GitHubCommitResponse gitHubCommitResponse = gitHubClient.fetchCommits(owner, repo, branch.commit().sha());
            if (gitHubCommitResponse.commit().committer().date().isAfter(link.getLastUpdated())) {
                List<Long> tgChatIds = (List<Long>) linkService.listAllChatsForLink(link.getUrl());
                botClient.sendUpdate(new LinkUpdateRequest(1L, link.getUrl(), "github_commit", tgChatIds));
            }
        }

        GitHubPullsResponse gitHubPullsResponse = gitHubClient.fetchPulls(owner, repo);
        if (gitHubPullsResponse.updatedAt().isAfter(link.getLastUpdated())) {
            List<Long> tgChatIds = (List<Long>) linkService.listAllChatsForLink(link.getUrl());
            botClient.sendUpdate(new LinkUpdateRequest(1L, link.getUrl(), "github_pull", tgChatIds));
        }
    }

    private void updateStackOverflow(Link link) {

    }
}
