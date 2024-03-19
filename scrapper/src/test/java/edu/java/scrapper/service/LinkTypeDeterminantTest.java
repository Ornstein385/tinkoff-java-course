package edu.java.scrapper.service;

import edu.java.service.LinkTypeDeterminant;
import java.net.URI;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LinkTypeDeterminantTest {

    @Test
    public void testIsGitHubCorrectLink() throws Exception {
        URI githubUri = new URI("https://github.com/Ornstein385/tinkoff-java-course");
        assertTrue(LinkTypeDeterminant.isGitHubCorrectLink(githubUri));

        URI githubBranchUri = new URI("https://github.com/Ornstein385/tinkoff-java-course/tree/hw5");
        assertTrue(LinkTypeDeterminant.isGitHubCorrectLink(githubBranchUri));
    }

    @Test
    public void testIsStackOverflowCorrectLink() throws Exception {
        URI stackOverflowUri = new URI(
            "https://stackoverflow.com/questions/63319222/spring-data-jpa-database-changes-after-each-method-dont-roll-back-in-integratio");
        assertTrue(LinkTypeDeterminant.isStackOverflowCorrectLink(stackOverflowUri));

        URI stackOverflowShortUri = new URI("https://stackoverflow.com/questions/63319222");
        assertTrue(LinkTypeDeterminant.isStackOverflowCorrectLink(stackOverflowShortUri));
    }

    @Test
    public void testGetGitHubOwner() throws Exception {
        URI githubUri = new URI("https://github.com/Ornstein385/tinkoff-java-course");
        assertEquals("Ornstein385", LinkTypeDeterminant.getGitHubOwner(githubUri));
    }

    @Test
    public void testGetGitHubRepo() throws Exception {
        URI githubUri = new URI("https://github.com/Ornstein385/tinkoff-java-course");
        assertEquals("tinkoff-java-course", LinkTypeDeterminant.getGitHubRepo(githubUri));
    }

    @Test
    public void testGetStackOverflowQuestionId() throws Exception {
        URI stackOverflowUri = new URI(
            "https://stackoverflow.com/questions/63319222/spring-data-jpa-database-changes-after-each-method-dont-roll-back-in-integratio");
        assertEquals("63319222", LinkTypeDeterminant.getStackOverflowQuestionId(stackOverflowUri));

        URI stackOverflowShortUri = new URI("https://stackoverflow.com/questions/63319222");
        assertEquals("63319222", LinkTypeDeterminant.getStackOverflowQuestionId(stackOverflowShortUri));
    }
}
