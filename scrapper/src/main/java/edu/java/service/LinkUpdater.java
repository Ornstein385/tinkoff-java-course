package edu.java.service;

import edu.java.client.BotClient;
import edu.java.client.GitHubClient;
import edu.java.client.StackOverflowClient;
import edu.java.configuration.ApplicationConfig;
import edu.java.dto.api.external.GitHubBranchesResponse;
import edu.java.dto.api.external.GitHubCommitResponse;
import edu.java.dto.api.external.GitHubPullsResponse;
import edu.java.dto.api.external.GitHubRepoResponse;
import edu.java.dto.api.external.StackOverflowQuestionResponse;
import edu.java.dto.api.internal.request.LinkUpdateRequest;
import edu.java.model.Link;
import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
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
        @Qualifier("jooqLinkService") LinkService linkService,
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
                applicationConfig.dataSource().maxResultLimit()
            ); //грузим ссылки частями
            for (Link link : linkList) {
                if (LinkTypeDeterminant.isGitHubCorrectLink(link.getUrl())) {
                    //updateGitHub(link);
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

        //бонусный тикет 1: появление новых веток/коммитов
        List<GitHubBranchesResponse> gitHubBranchesResponses = gitHubClient.fetchBranches(owner, repo);
        for (GitHubBranchesResponse branch : gitHubBranchesResponses) {
            GitHubCommitResponse gitHubCommitResponse = gitHubClient.fetchCommits(owner, repo, branch.commit().sha());
            if (gitHubCommitResponse.commit().committer().date().isAfter(link.getLastUpdated())) {
                List<Long> tgChatIds = (List<Long>) linkService.listAllChatsForLink(link.getUrl());
                botClient.sendUpdate(new LinkUpdateRequest(1L, link.getUrl(), "github_commit", tgChatIds));
            }
        }

        //бонусный тикет 2: появление новых PR
        List<GitHubPullsResponse> gitHubPullsResponses = gitHubClient.fetchPulls(owner, repo);
        for (GitHubPullsResponse pull : gitHubPullsResponses) {
            if (pull.updatedAt().isAfter(link.getLastUpdated())) {
                List<Long> tgChatIds = (List<Long>) linkService.listAllChatsForLink(link.getUrl());
                botClient.sendUpdate(new LinkUpdateRequest(1L, link.getUrl(), "github_pull", tgChatIds));
            }
        }

        //можно удалить github_repo (обычная проверка репозитория) т к это покрывается github_commit и не имеет смысла
        GitHubRepoResponse gitHubRepoResponse = gitHubClient.fetchRepository(owner, repo);
        if (gitHubRepoResponse.updatedAt().isAfter(link.getLastUpdated())) {
            List<Long> tgChatIds = (List<Long>) linkService.listAllChatsForLink(link.getUrl());
            botClient.sendUpdate(new LinkUpdateRequest(1L, link.getUrl(), "github_repo", tgChatIds));
        }
    }

    private void updateStackOverflow(Link link) {
        String questionId = LinkTypeDeterminant.getStackOverflowQuestionId(link.getUrl());
        StackOverflowQuestionResponse response = stackOverflowClient.fetchQuestion(questionId);
        if (response.items().getFirst().lastEditDate().isAfter(link.getLastUpdated())) {
            List<Long> tgChatIds = (List<Long>) linkService.listAllChatsForLink(link.getUrl());
            botClient.sendUpdate(new LinkUpdateRequest(1L, link.getUrl(), "stackoverflow_question", tgChatIds));
        }
    }
}
