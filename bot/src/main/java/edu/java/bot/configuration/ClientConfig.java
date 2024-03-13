package edu.java.bot.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ClientConfig {

    private String defaultScrapperBaseUrl = "http://localhost:8091";

    @Bean
    public WebClient scrapperWebClient(@Value("${scrapper.base.url:#{null}}") String baseUrl) {
        return WebClient.builder()
            .baseUrl(baseUrl != null ? baseUrl : defaultScrapperBaseUrl)
            .build();
    }
}
