package edu.java.scrapper.client;

import edu.java.client.StackOverflowClient;
import edu.java.client.StackOverflowClientImpl;
import edu.java.configuration.ClientConfig;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import edu.java.dto.StackOverflowQuestionResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.ResourceUtils;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.testcontainers.shaded.org.apache.commons.io.FileUtils;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class StackOverflowClientTest extends ClientTest {
    private StackOverflowClient stackOverflowClient;

    private final String questionId = "59771574";

    @BeforeEach
    public void setUp() {
        stackOverflowClient = new StackOverflowClientImpl(new ClientConfig().stackOverflowWebClient(baseUrl));
    }

    @Test
    public void testGettingQuestionInfo() throws IOException {
        File file = ResourceUtils.getFile("classpath:stackoverflowQuestionResponse.json");
        String json = FileUtils.readFileToString(file, StandardCharsets.UTF_8);

        wireMockServer
            .stubFor(get("/questions/%s?site=stackoverflow".formatted(questionId))
                .willReturn(aResponse()
                    .withStatus(200)
                    .withBody(json)
                    .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)));

        StackOverflowQuestionResponse response = stackOverflowClient.fetchQuestion(questionId);

        assertNotNull(response);
        assertThat(response.items()).isNotEmpty();
        assertThat(response.items().getFirst().questionId()).isEqualTo(Long.parseLong(questionId));
    }

    @Test
    public void testResponseOnInvalidQuestion() throws IOException {
        File file = ResourceUtils.getFile("classpath:stackoverflowQuestionNotFound.json");
        String json = FileUtils.readFileToString(file, StandardCharsets.UTF_8);

        String invalidQuestionId = "88005553535";

        wireMockServer
            .stubFor(get("/questions/%s?site=stackoverflow".formatted(questionId))
                .willReturn(aResponse()
                    .withStatus(400)
                    .withBody(json)
                    .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)));

        assertThatExceptionOfType(WebClientResponseException.class)
            .isThrownBy(() -> stackOverflowClient.fetchQuestion(invalidQuestionId));
    }
}
