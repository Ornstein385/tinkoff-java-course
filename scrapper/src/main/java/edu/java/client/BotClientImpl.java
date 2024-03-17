package edu.java.client;

import edu.java.dto.api.internal.request.LinkUpdateRequest;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class BotClientImpl implements BotClient {

    private final WebClient webClient;

    public BotClientImpl(@Qualifier("botWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public String sendUpdate(LinkUpdateRequest linkUpdateRequest) {
        return webClient.post()
            .uri("/updates")
            .bodyValue(linkUpdateRequest)
            .retrieve()
            .bodyToMono(String.class)
            .block(); // блокирующий вызов, можно использовать асинхронно
    }
}
