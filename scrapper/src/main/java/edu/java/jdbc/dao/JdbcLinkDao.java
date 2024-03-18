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
    final String uri = "uri";
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
            rs.getString(uri),
            rs.getObject(lastUpdated, OffsetDateTime.class)
        )).stream().findAny();
    }

    public List<LinkDto> findAllLinks() {
        String sql = "SELECT * FROM links";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new LinkDto(
            rs.getLong(id),
            rs.getString(uri),
            rs.getObject(lastUpdated, OffsetDateTime.class)
        ));
    }
}
