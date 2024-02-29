package edu.java.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ClientConfig {

    private String defaultGithubBaseUrl = "https://api.github.com";
    private String defaultStackOverflowBaseUrl = "https://api.stackexchange.com/2.3";

    @Bean
    public WebClient githubWebClient(@Value("${github.base.url:#{null}}") String baseUrl) {
        return WebClient.builder()
            .baseUrl(baseUrl != null ? baseUrl : defaultGithubBaseUrl)
            .build();
    }

    @Bean
    public WebClient stackOverflowWebClient(@Value("${stackoverflow.base.url:#{null}}") String baseUrl) {
        return WebClient.builder()
            .baseUrl(baseUrl != null ? baseUrl : defaultStackOverflowBaseUrl)
            .build();
    }
}
