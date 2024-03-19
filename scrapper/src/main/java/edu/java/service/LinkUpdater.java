package edu.java.service;

import edu.java.client.GitHubClient;
import edu.java.client.StackOverflowClient;
import org.springframework.stereotype.Service;

@Service
public class LinkUpdater {
    StackOverflowClient stackOverflowClient;
    GitHubClient gitHubClient;
    LinkService linkService;

    public LinkUpdater(StackOverflowClient stackOverflowClient, GitHubClient gitHubClient, LinkService linkService) {
        this.stackOverflowClient = stackOverflowClient;
        this.gitHubClient = gitHubClient;
        this.linkService = linkService;
    }

    public void update(){

    }
}
