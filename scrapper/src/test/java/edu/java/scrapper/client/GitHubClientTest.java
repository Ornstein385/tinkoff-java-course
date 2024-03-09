package edu.java.scrapper.client;

import edu.java.client.GitHubClient;
import edu.java.client.GitHubClientImpl;
import edu.java.configuration.ClientConfig;
import edu.java.dto.GitHubRepoResponse;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
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

public class GitHubClientTest extends ClientTest {
    private GitHubClient gitHubClient;

    private final String repositoryName = "tinkoff-java-course";

    private final String userName = "Ornstein385";

    @BeforeEach
    public void setUp() {
        gitHubClient = new GitHubClientImpl(new ClientConfig().githubWebClient(baseUrl));
    }

    @Test
    public void testGettingRepositoryInfo() throws IOException {
        File file = ResourceUtils.getFile("classpath:githubRepoResponse.json");
        String json = FileUtils.readFileToString(file, StandardCharsets.UTF_8);

        wireMockServer
            .stubFor(get("/repos/%s/%s".formatted(userName, repositoryName))
                .willReturn(aResponse()
                    .withStatus(200)
                    .withBody(json)
                    .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)));

        GitHubRepoResponse response = gitHubClient.fetchRepository(userName, repositoryName);

        assertNotNull(response);
        assertThat(response.id()).isEqualTo(752910150L);
        assertThat(response.owner().login()).isEqualTo(userName);
        assertThat(response.name()).isEqualTo(repositoryName);
    }

    @Test
    public void testResponseOnInvalidRepository() throws IOException {
        File file = ResourceUtils.getFile("classpath:githubRepoNotFound.json");
        String json = FileUtils.readFileToString(file, StandardCharsets.UTF_8);

        String invalidUser = "invalidUser";
        String invalidRepo = "invalidRepo";

        wireMockServer
            .stubFor(get("/repos/%s/%s".formatted(invalidUser, invalidRepo))
                .willReturn(aResponse()
                    .withStatus(404)
                    .withBody(json)
                    .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)));

        assertThatExceptionOfType(WebClientResponseException.class)
            .isThrownBy(() -> gitHubClient.fetchRepository(invalidUser, invalidRepo));
    }
}
