package edu.java.scrapper.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
public class LiquibaseIntegrationTest extends IntegrationTest {
    private static String expectedUrl = "https://github.com/Ornstein385/tinkoff-java-course";
    private static Long expectedChatId = 123456L;

    @BeforeEach
    public void fillTables() {
        jdbcTemplate.update("INSERT INTO links (url) VALUES (?) ON CONFLICT (url) DO NOTHING", expectedUrl);
        jdbcTemplate.update("INSERT INTO chats (id) VALUES (?) ON CONFLICT (id) DO NOTHING", expectedChatId);
    }

    @Test
    public void testDatabaseConnection() {
        assertTrue(postgres.isRunning());
        assertThat(postgres.getUsername()).isEqualTo("postgres");
        assertThat(postgres.getPassword()).isEqualTo("postgres");
        assertThat(postgres.getDatabaseName()).isEqualTo("scrapper");
    }

    @Test
    public void testLinkInsertion() {
        Long actualLinkId = jdbcTemplate.queryForObject("SELECT id FROM links WHERE url = ?", Long.class, expectedUrl);
        assertThat(actualLinkId).isNotNull();
        String actualUrl =
            jdbcTemplate.queryForObject("SELECT url FROM links WHERE id = ?", String.class, actualLinkId);
        assertThat(actualUrl).isEqualTo(expectedUrl);
    }

    @Test
    public void testChatInsertion() {
        Integer count =
            jdbcTemplate.queryForObject("SELECT COUNT(*) FROM chats WHERE id = ?", Integer.class, expectedChatId);
        assertThat(count).isEqualTo(1);
    }

    @Test
    public void testLinkChatAssociation() {
        Long actualLinkId = jdbcTemplate.queryForObject("SELECT id FROM links WHERE url = ?", Long.class, expectedUrl);
        jdbcTemplate.update(
            "INSERT INTO link_chat (chat_id, link_id) VALUES(?, ?) ON CONFLICT DO NOTHING",
            expectedChatId,
            actualLinkId
        );
        Integer count = jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM link_chat WHERE chat_id = ? AND link_id = ?",
            Integer.class,
            expectedChatId,
            actualLinkId
        );
        assertThat(count).isEqualTo(1);
    }
}
