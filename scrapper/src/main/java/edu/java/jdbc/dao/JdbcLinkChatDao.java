package edu.java.jdbc.dao;

import edu.java.jdbc.dto.LinkChatDto;
import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class JdbcLinkChatDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcLinkChatDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public void addLinkChat(LinkChatDto linkChatDto) {
        String sql = "INSERT INTO link_chat (link_id, chat_id, added_at) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, linkChatDto.getLinkId(), linkChatDto.getChatId(), linkChatDto.getAddedAt());
    }

    @Transactional
    public void removeLinkChat(Long linkId, Long chatId) {
        String sql = "DELETE FROM link_chat WHERE link_id = ? AND chat_id = ?";
        jdbcTemplate.update(sql, linkId, chatId);
    }

    public List<LinkChatDto> findAllLinkChats() {
        String sql = "SELECT * FROM link_chat";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new LinkChatDto(
            rs.getLong("link_id"),
            rs.getLong("chat_id"),
            rs.getObject("added_at", OffsetDateTime.class)
        ));
    }
}
