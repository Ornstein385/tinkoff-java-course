package edu.java.scheduling;

import edu.java.service.LinkUpdater;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
@Slf4j
public class LinkUpdaterScheduler {

    LinkUpdater linkUpdater;

    @Autowired
    public LinkUpdaterScheduler(LinkUpdater linkUpdater) {
        this.linkUpdater = linkUpdater;
    }

    @Scheduled(fixedDelayString =
        "#{beanFactory.getBean(T(edu.java.configuration.ApplicationConfig)).scheduler.interval}")
    //@Scheduled(fixedDelayString = "#{@('app-edu.java.configuration.ApplicationConfig').scheduler.interval}")
    public void update() {
        linkUpdater.update();
    }
}
