package edu.java.bot.client;

import edu.java.bot.dto.api.request.AddLinkRequest;
import edu.java.bot.dto.api.request.RemoveLinkRequest;
import edu.java.bot.dto.api.response.LinkResponse;
import edu.java.bot.dto.api.response.ListLinksResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class ScrapperClientImpl implements ScrapperClient {

    private static final String LINKS_ENDPOINT = "/links";
    private static final String TG_CHAT_ENDPOINT = "/tg-chat/";
    private static final String TG_CHAT_ID_HEADER = "Tg-Chat-Id";

    private final WebClient webClient;

    public ScrapperClientImpl(@Qualifier("scrapperWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public ListLinksResponse getAllLinks(Long tgChatId) {
        return webClient.get()
            .uri(LINKS_ENDPOINT)
            .header(TG_CHAT_ID_HEADER, tgChatId.toString())
            .retrieve()
            .bodyToMono(ListLinksResponse.class)
            .block(); // Blocking call, can be used asynchronously
    }

    @Override
    public LinkResponse addLink(Long tgChatId, AddLinkRequest addLinkRequest) {
        return webClient.post()
            .uri(LINKS_ENDPOINT)
            .header(TG_CHAT_ID_HEADER, tgChatId.toString())
            .bodyValue(addLinkRequest)
            .retrieve()
            .bodyToMono(LinkResponse.class)
            .block();
    }

    @Override
    public String removeLink(Long tgChatId, RemoveLinkRequest removeLinkRequest) {
        return webClient.method(HttpMethod.DELETE)
            .uri(LINKS_ENDPOINT)
            .header(TG_CHAT_ID_HEADER, tgChatId.toString())
            .body(BodyInserters.fromValue(removeLinkRequest)) // Using BodyInserters for request body
            .retrieve()
            .bodyToMono(String.class)
            .block();
    }

    @Override
    public String registerChat(Long id) {
        return webClient.post()
            .uri(TG_CHAT_ENDPOINT + id)
            .retrieve()
            .bodyToMono(String.class)
            .block();
    }

    @Override
    public String deleteChat(Long id) {
        return webClient.delete()
            .uri(TG_CHAT_ENDPOINT + id)
            .retrieve()
            .bodyToMono(String.class)
            .block();
    }
}

