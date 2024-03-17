package edu.java.jdbc.dao;

import edu.java.jdbc.dto.LinkDto;
import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class JdbcLinkDao {

    private final JdbcTemplate jdbcTemplate;

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
    public void removeLink(Long linkId) {
        String sql = "DELETE FROM links WHERE id = ?";
        jdbcTemplate.update(sql, linkId);
    }

    public List<LinkDto> findAllLinks() {
        String sql = "SELECT * FROM links";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new LinkDto(
            rs.getLong("id"),
            rs.getString("url"),
            rs.getObject("last_updated", OffsetDateTime.class)
        ));
    }
}
