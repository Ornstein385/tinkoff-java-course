package edu.java.service;

import edu.java.client.BotClient;
import edu.java.client.GitHubClient;
import edu.java.client.StackOverflowClient;
import edu.java.configuration.ApplicationConfig;
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
        //реализовано через бонусное задание
    }

    private void updateStackOverflow(Link link) {
        //реализовано через бонусное задание
    }
}
