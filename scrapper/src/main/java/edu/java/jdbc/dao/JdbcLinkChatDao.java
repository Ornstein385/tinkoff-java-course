package edu.java.jdbc.dao;

import edu.java.jdbc.dto.ChatDto;
import edu.java.jdbc.dto.LinkChatDto;
import edu.java.jdbc.dto.LinkDto;
import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class JdbcLinkChatDao {

    private final JdbcTemplate jdbcTemplate;

    final String id = "id";
    final String uri = "uri";
    final String lastUpdated = "last_updated";
    final String createdAt = "created_at";

    @Autowired
    public JdbcLinkChatDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public void addLinkChat(LinkChatDto linkChatDto) {
        String sql = """
                     INSERT INTO link_chat (link_id, chat_id)
                     VALUES (?, ?)
                     """;
        jdbcTemplate.update(sql, linkChatDto.getLinkId(), linkChatDto.getChatId());
    }

    @Transactional
    public void removeLinkChat(Long linkId, Long chatId) {
        String sql = """
                     DELETE FROM link_chat
                     WHERE link_id = ? AND chat_id = ?
                     """;
        jdbcTemplate.update(sql, linkId, chatId);
    }

    public List<LinkChatDto> findAllLinkChats() {
        String sql = """
                     SELECT *
                     FROM link_chat
                     """;
        return jdbcTemplate.query(sql, (rs, rowNum) -> new LinkChatDto(
            rs.getLong("link_id"),
            rs.getLong("chat_id"),
            rs.getObject("added_at", OffsetDateTime.class)
        ));
    }

    public List<LinkDto> findAllLinksForChat(Long chatId) {
        String sql = """
                     SELECT l.id, l.url, l.last_updated
                     FROM link_chat lc
                     JOIN links l ON lc.link_id = l.id
                     WHERE lc.chat_id = ?
                     """;
        return jdbcTemplate.query(sql, new Object[]{chatId}, (rs, rowNum) -> new LinkDto(
            rs.getLong(id),
            rs.getString(uri),
            rs.getObject(lastUpdated, OffsetDateTime.class)
        ));
    }

    public List<LinkDto> findAllLinksForChat(Long chatId, long millisecondsBack, int limit) {
        String sql = """
                     SELECT l.id, l.url, l.last_updated
                     FROM link_chat lc
                     JOIN links l ON lc.link_id = l.id
                     WHERE lc.chat_id = ? AND l.last_updated <= NOW() - INTERVAL '? milliseconds'
                     ORDER BY l.last_updated ASC
                     LIMIT ?
                     """;
        return jdbcTemplate.query(sql, new Object[]{chatId, millisecondsBack, limit}, (rs, rowNum) -> new LinkDto(
            rs.getLong(id),
            rs.getString(uri),
            rs.getObject(lastUpdated, OffsetDateTime.class)
        ));
    }

    public List<ChatDto> findAllChatsForLink(Long linkId) {
        String sql = """
                     SELECT c.id, c.created_at
                     FROM link_chat lc
                     JOIN chats c ON lc.chat_id = c.id
                     WHERE lc.link_id = ?
                     """;
        return jdbcTemplate.query(sql, new Object[]{linkId}, (rs, rowNum) -> new ChatDto(
            rs.getLong(id),
            rs.getObject(createdAt, OffsetDateTime.class)
        ));
    }
}
