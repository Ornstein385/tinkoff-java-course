package edu.java.jdbc.dao;

import edu.java.jdbc.dto.LinkDto;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class JdbcLinkDao {

    private final JdbcTemplate jdbcTemplate;

    final String id = "id";
    final String url = "url";
    final String lastUpdated = "last_updated";

    @Autowired
    public JdbcLinkDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public void addLink(LinkDto linkDTO) {
        String sql = "INSERT INTO links (url, last_updated) VALUES (?, ?)";
        jdbcTemplate.update(sql, linkDTO.getUrl(), linkDTO.getLastUpdated());
    }

    @Transactional
    public void removeLink(String url) {
        String sql = "DELETE FROM links WHERE url = ?";
        jdbcTemplate.update(sql, url);
    }

    @Transactional
    public Optional<LinkDto> getLink(String url) {
        String sql = "SELECT * FROM links WHERE url = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new LinkDto(
            rs.getLong(id),
            rs.getString(this.url),
            rs.getObject(lastUpdated, OffsetDateTime.class)
        )).stream().findAny();
    }

    @Transactional
    public List<LinkDto> findAllLinks() {
        String sql = "SELECT * FROM links";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new LinkDto(
            rs.getLong(id),
            rs.getString(url),
            rs.getObject(lastUpdated, OffsetDateTime.class)
        ));
    }

    @Transactional
    public List<LinkDto> findAllLinks(long millisecondsBack, int limit) {
        String sql = """
            SELECT * FROM links
            WHERE last_updated <= NOW() - INTERVAL '? milliseconds'
            ORDER BY last_updated ASC
            LIMIT ?
            """;
        return jdbcTemplate.query(sql, new Object[] {millisecondsBack, limit}, (rs, rowNum) -> new LinkDto(
            rs.getLong(id),
            rs.getString(url),
            rs.getObject(lastUpdated, OffsetDateTime.class)
        ));
    }

    @Transactional
    public void updateLinkLastUpdatedTime(String url) {
        String sql = "UPDATE links SET last_updated = CURRENT_TIMESTAMP WHERE url = ?";
        jdbcTemplate.update(sql, url);
    }
}
