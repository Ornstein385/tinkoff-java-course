package edu.java.scrapper.integration.jooq;

import edu.java.jooq.dao.JooqLinkDao;
import edu.java.jooq.dto.LinkDto;
import edu.java.scrapper.integration.IntegrationTest;

import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;
import org.junit.jupiter.api.Assertions;
import java.time.OffsetDateTime;

public class JooqLinkDaoTest extends IntegrationTest {
    private JooqLinkDao linkDao = new JooqLinkDao(dslContext);

    private static String expectedUrl = "https://github.com/Ornstein385/tinkoff-java-course";

    @Test
    @Transactional
    void addAndFindAllTest() {
        LinkDto linkDto = new LinkDto(0L, expectedUrl, OffsetDateTime.now());
        linkDao.addLink(linkDto);
        var linkDtos = linkDao.findAllLinks();
        Assertions.assertEquals(1, linkDtos == null ? 0 : linkDtos.size());
    }

    @Test
    @Transactional
    void removeAndFindAllTest() {
        LinkDto linkDto = new LinkDto(0L, expectedUrl, OffsetDateTime.now());
        linkDao.addLink(linkDto);
        linkDao.removeLink(expectedUrl);
        var linkDtos = linkDao.findAllLinks();
        Assertions.assertEquals(0, linkDtos == null ? 0 : linkDtos.size());
    }
}
