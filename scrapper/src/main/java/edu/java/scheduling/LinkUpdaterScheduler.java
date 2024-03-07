package edu.java.scheduling;

import edu.java.client.GitHubClient;
import edu.java.client.StackOverflowClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component @EnableScheduling @Slf4j public class LinkUpdaterScheduler {

    StackOverflowClient stackOverflowClient;
    GitHubClient gitHubClient;

    public LinkUpdaterScheduler(StackOverflowClient stackOverflowClient, GitHubClient gitHubClient) {
        this.stackOverflowClient = stackOverflowClient;
        this.gitHubClient = gitHubClient;
    }

    @Scheduled(fixedDelayString =
        "#{beanFactory.getBean(T(edu.java.configuration.ApplicationConfig)).scheduler.interval}")
    //@Scheduled(fixedDelayString = "#{@app-edu.java.configuration.ApplicationConfig.scheduler.interval}")
    public void update() {
        log.info("Updating info...");
        stackOverflowClient.fetchQuestion("59771574")
            .subscribe(stackOverflowQuestionResponse -> log.info(stackOverflowQuestionResponse.toString()));
        gitHubClient.fetchRepository("Ornstein385", "tinkoff-java-course")
            .subscribe(gitHubRepoResponse -> log.info(gitHubRepoResponse.toString()));
    }
}
