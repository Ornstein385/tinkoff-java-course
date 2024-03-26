package edu.java.scrapper.integration.jooq;

import edu.java.jooq.dao.JooqChatDao;
import edu.java.jooq.dto.ChatDto;
import edu.java.scrapper.integration.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;
import org.junit.jupiter.api.Assertions;
import java.time.OffsetDateTime;

public class JooqChatDaoTest extends IntegrationTest {
    private JooqChatDao chatDao = new JooqChatDao(dslContext);

    @Test
    @Transactional
    void addAndFindAllTest() {
        ChatDto chatDto = new ChatDto(123456L, OffsetDateTime.now());
        chatDao.addChat(chatDto);
        var chatDtos = chatDao.findAllChats();
        Assertions.assertEquals(1, chatDtos == null ? 0 : chatDtos.size());
    }

    @Test
    @Transactional
    void removeAndFindAllTest() {
        ChatDto chatDto = new ChatDto(123457L, OffsetDateTime.now());
        chatDao.addChat(chatDto);
        chatDao.removeChat(123457L);
        var chatDtos = chatDao.findAllChats();
        Assertions.assertEquals(0, chatDtos == null ? 0 : chatDtos.size());
    }
}
