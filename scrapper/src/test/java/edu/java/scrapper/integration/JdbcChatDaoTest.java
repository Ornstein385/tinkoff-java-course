package edu.java.scrapper.integration;

import edu.java.jdbc.dao.JdbcChatDao;
import edu.java.jdbc.dto.ChatDto;
import java.time.OffsetDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JdbcChatDaoTest extends IntegrationTest {
    private JdbcChatDao chatDao = new JdbcChatDao(jdbcTemplate);

    private static Long tgId = 123456L;

    @Test
    @Transactional
    @Rollback
    void addAndFindAllTest() {
        ChatDto chatDto = new ChatDto(tgId, OffsetDateTime.now());
        chatDao.addChat(chatDto);
        List<ChatDto> chatDtos = chatDao.findAllChats();
        assertEquals(1, chatDtos == null ? 0 : chatDtos.size());
    }

    @Test
    @Transactional
    @Rollback
    void removeAndFindAllTest() {
        ChatDto chatDto = new ChatDto(tgId, OffsetDateTime.now());
        chatDao.addChat(chatDto);
        chatDao.removeChat(tgId);
        List<ChatDto> chatDtos = chatDao.findAllChats();
        assertEquals(0, chatDtos == null ? 0 : chatDtos.size());
    }
}
