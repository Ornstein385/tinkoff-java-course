package edu.java.scrapper.integration;

import edu.java.jdbc.dao.JdbcChatDao;
import edu.java.jdbc.dao.JdbcLinkChatDao;
import edu.java.jdbc.dao.JdbcLinkDao;
import edu.java.jdbc.dto.ChatDto;
import edu.java.jdbc.dto.LinkChatDto;
import edu.java.jdbc.dto.LinkDto;
import java.time.OffsetDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JdbcLinkChatDaoTest extends IntegrationTest {
    JdbcChatDao chatDao = new JdbcChatDao(jdbcTemplate);
    JdbcLinkDao linkDao = new JdbcLinkDao(jdbcTemplate);
    JdbcLinkChatDao linkChatDao = new JdbcLinkChatDao(jdbcTemplate);

    private static String expectedUrl = "https://github.com/Ornstein385/tinkoff-java-course";

    @Test
    @Transactional
    @Rollback
    void addAndFindAllTest() {
        Long tgId = 123456L;
        ChatDto chatDto = new ChatDto(tgId, OffsetDateTime.now());
        chatDao.addChat(chatDto);
        LinkDto linkDto = new LinkDto(0L, expectedUrl, OffsetDateTime.now());
        linkDao.addLink(linkDto);
        Long linkId = jdbcTemplate.queryForObject(
            "SELECT id FROM links WHERE url = ?",
            new Object[] {linkDto.getUrl()}, Long.class
        );
        LinkChatDto linkChatDto = new LinkChatDto(linkId, tgId, OffsetDateTime.now());
        linkChatDao.addLinkChat(linkChatDto);
        List<LinkChatDto> linkChatDtos = linkChatDao.findAllLinkChats();
        assertEquals(1, linkChatDtos == null ? 0 : linkChatDtos.size());
    }

    @Test
    @Transactional
    @Rollback
    void removeAndFindAllTest() {
        Long tgId = 123457L;
        ChatDto chatDto = new ChatDto(tgId, OffsetDateTime.now());
        chatDao.addChat(chatDto);
        LinkDto linkDto = new LinkDto(0L, expectedUrl, OffsetDateTime.now());
        linkDao.addLink(linkDto);
        Long linkId = jdbcTemplate.queryForObject(
            "SELECT id FROM links WHERE url = ?",
            new Object[] {linkDto.getUrl()}, Long.class
        );
        LinkChatDto linkChatDto = new LinkChatDto(linkId, tgId, OffsetDateTime.now());
        linkChatDao.addLinkChat(linkChatDto);
        linkChatDao.removeLinkChat(linkId, tgId);
        List<LinkChatDto> linkChatDtos = linkChatDao.findAllLinkChats();
        assertEquals(0, linkChatDtos == null ? 0 : linkChatDtos.size());
    }

    @Test
    @Transactional
    @Rollback
    void removeCascadeLinkAllTest() {
        Long tgId = 123458L;
        ChatDto chatDto = new ChatDto(tgId, OffsetDateTime.now());
        chatDao.addChat(chatDto);
        LinkDto linkDto = new LinkDto(0L, expectedUrl, OffsetDateTime.now());
        linkDao.addLink(linkDto);
        Long linkId = jdbcTemplate.queryForObject(
            "SELECT id FROM links WHERE url = ?",
            new Object[] {linkDto.getUrl()}, Long.class
        );
        LinkChatDto linkChatDto = new LinkChatDto(linkId, tgId, OffsetDateTime.now());
        linkChatDao.addLinkChat(linkChatDto);
        linkDao.removeLink(expectedUrl);
        List<LinkChatDto> linkChatDtos = linkChatDao.findAllLinkChats();
        List<ChatDto> chatDtos = chatDao.findAllChats();
        List<LinkDto> linkDtos = linkDao.findAllLinks();
        assertEquals(0, linkChatDtos == null ? 0 : linkChatDtos.size());
        assertEquals(1, chatDtos == null ? 0 : chatDtos.size());
        assertEquals(0, linkDtos == null ? 0 : linkDtos.size());
    }

    @Test
    @Transactional
    @Rollback
    void removeCascadeChatAllTest() {
        Long tgId = 123459L;
        ChatDto chatDto = new ChatDto(tgId, OffsetDateTime.now());
        chatDao.addChat(chatDto);
        LinkDto linkDto = new LinkDto(0L, expectedUrl, OffsetDateTime.now());
        linkDao.addLink(linkDto);
        Long linkId = jdbcTemplate.queryForObject(
            "SELECT id FROM links WHERE url = ?",
            new Object[] {linkDto.getUrl()}, Long.class
        );
        LinkChatDto linkChatDto = new LinkChatDto(linkId, tgId, OffsetDateTime.now());
        linkChatDao.addLinkChat(linkChatDto);
        chatDao.removeChat(tgId);
        List<LinkChatDto> linkChatDtos = linkChatDao.findAllLinkChats();
        List<ChatDto> chatDtos = chatDao.findAllChats();
        List<LinkDto> linkDtos = linkDao.findAllLinks();
        assertEquals(0, linkChatDtos == null ? 0 : linkChatDtos.size());
        assertEquals(0, chatDtos == null ? 0 : chatDtos.size());
        assertEquals(1, linkDtos == null ? 0 : linkDtos.size());
    }
}
