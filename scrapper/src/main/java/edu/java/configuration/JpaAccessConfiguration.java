package edu.java.configuration;

import edu.java.jpa.repository.JpaChatRepository;
import edu.java.jpa.repository.JpaLinkChatRepository;
import edu.java.jpa.repository.JpaLinkRepository;
import edu.java.jpa.service.JpaLinkService;
import edu.java.service.LinkService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jpa")
public class JpaAccessConfiguration {
    @Bean
    public LinkService linkService(
        JpaLinkRepository jpaLinkRepository,
        JpaChatRepository jpaChatRepository,
        JpaLinkChatRepository jpaLinkChatRepository
    ) {
        return new JpaLinkService(jpaLinkRepository, jpaChatRepository, jpaLinkChatRepository);
    }
}
