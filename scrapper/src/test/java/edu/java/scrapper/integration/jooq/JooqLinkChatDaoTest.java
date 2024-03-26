package edu.java.scrapper.integration.jooq;

import edu.java.jooq.dao.JooqChatDao;
import edu.java.jooq.dao.JooqLinkChatDao;
import edu.java.jooq.dao.JooqLinkDao;
import edu.java.jooq.dto.ChatDto;
import edu.java.jooq.dto.LinkChatDto;
import edu.java.jooq.dto.LinkDto;
import edu.java.scrapper.integration.IntegrationTest;
import java.time.OffsetDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

public class JooqLinkChatDaoTest extends IntegrationTest {
    JooqChatDao chatDao = new JooqChatDao(dslContext);
    JooqLinkDao linkDao = new JooqLinkDao(dslContext);
    JooqLinkChatDao linkChatDao = new JooqLinkChatDao(dslContext);

    private static String expectedUrl = "https://github.com/Ornstein385/tinkoff-java-course";

    @Test
    @Transactional
    void addAndFindAllTest() {
        Long tgId = 123456L;
        ChatDto chatDto = new ChatDto(tgId, OffsetDateTime.now());
        chatDao.addChat(chatDto);
        LinkDto linkDto = new LinkDto(0L, expectedUrl, OffsetDateTime.now());
        linkDao.addLink(linkDto);
        Long linkId = linkDao.getLink(linkDto.getUrl()).get().getId();
        LinkChatDto linkChatDto = new LinkChatDto(linkId, tgId, OffsetDateTime.now());
        linkChatDao.addLinkChat(linkChatDto);
        var linkChatDtos = linkChatDao.findAllLinkChats();
        Assertions.assertEquals(1, linkChatDtos == null ? 0 : linkChatDtos.size());
    }

    @Test
    @Transactional
    void removeAndFindAllTest() {
        Long tgId = 123457L;
        ChatDto chatDto = new ChatDto(tgId, OffsetDateTime.now());
        chatDao.addChat(chatDto);
        LinkDto linkDto = new LinkDto(0L, expectedUrl, OffsetDateTime.now());
        linkDao.addLink(linkDto);
        Long linkId = linkDao.getLink(linkDto.getUrl()).get().getId();
        LinkChatDto linkChatDto = new LinkChatDto(linkId, tgId, OffsetDateTime.now());
        linkChatDao.addLinkChat(linkChatDto);
        linkChatDao.removeLinkChat(linkId, tgId);
        var linkChatDtos = linkChatDao.findAllLinkChats();
        Assertions.assertEquals(0, linkChatDtos == null ? 0 : linkChatDtos.size());
    }

    @Test
    @Transactional
    void removeCascadeLinkAllTest() {
        Long tgId = 123458L;
        ChatDto chatDto = new ChatDto(tgId, OffsetDateTime.now());
        chatDao.addChat(chatDto);
        LinkDto linkDto = new LinkDto(0L, expectedUrl, OffsetDateTime.now());
        linkDao.addLink(linkDto);
        Long linkId = linkDao.getLink(linkDto.getUrl()).get().getId();
        LinkChatDto linkChatDto = new LinkChatDto(linkId, tgId, OffsetDateTime.now());
        linkChatDao.addLinkChat(linkChatDto);
        linkDao.removeLink(expectedUrl);
        var linkChatDtos = linkChatDao.findAllLinkChats();
        var chatDtos = chatDao.findAllChats();
        var linkDtos = linkDao.findAllLinks();
        Assertions.assertEquals(0, linkChatDtos == null ? 0 : linkChatDtos.size());
        Assertions.assertEquals(1, chatDtos == null ? 0 : chatDtos.size());
        Assertions.assertEquals(0, linkDtos == null ? 0 : linkDtos.size());
    }

    @Test
    @Transactional
    void removeCascadeChatAllTest() {
        Long tgId = 123459L;
        ChatDto chatDto = new ChatDto(tgId, OffsetDateTime.now());
        chatDao.addChat(chatDto);
        LinkDto linkDto = new LinkDto(0L, expectedUrl, OffsetDateTime.now());
        linkDao.addLink(linkDto);
        Long linkId = linkDao.getLink(linkDto.getUrl()).get().getId();
        LinkChatDto linkChatDto = new LinkChatDto(linkId, tgId, OffsetDateTime.now());
        linkChatDao.addLinkChat(linkChatDto);
        chatDao.removeChat(tgId);
        var linkChatDtos = linkChatDao.findAllLinkChats();
        var chatDtos = chatDao.findAllChats();
        var linkDtos = linkDao.findAllLinks();
        Assertions.assertEquals(0, linkChatDtos == null ? 0 : linkChatDtos.size());
        Assertions.assertEquals(0, chatDtos == null ? 0 : chatDtos.size());
        Assertions.assertEquals(1, linkDtos == null ? 0 : linkDtos.size());
    }
}

