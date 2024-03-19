package edu.java.scrapper.integration;

import edu.java.jdbc.dao.JdbcLinkDao;
import edu.java.jdbc.dto.LinkDto;
import java.time.OffsetDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JdbcLinkDaoTest extends IntegrationTest {

    private JdbcLinkDao linkDao = new JdbcLinkDao(jdbcTemplate);

    private static String expectedUrl = "https://github.com/Ornstein385/tinkoff-java-course";

    @Test
    @Transactional
    @Rollback
    void addAndFindAllTest() {
        LinkDto linkDto = new LinkDto(0L, expectedUrl, OffsetDateTime.now());
        linkDao.addLink(linkDto);
        List<LinkDto> linkDtos = linkDao.findAllLinks();
        assertEquals(1, linkDtos == null ? 0 : linkDtos.size());
    }

    @Test
    @Transactional
    @Rollback
    void removeAndFindAllTest() {
        LinkDto linkDto = new LinkDto(0L, expectedUrl, OffsetDateTime.now());
        linkDao.addLink(linkDto);
        linkDao.removeLink(expectedUrl);
        List<LinkDto> linkDtos = linkDao.findAllLinks();
        assertEquals(0, linkDtos == null ? 0 : linkDtos.size());
    }
}
