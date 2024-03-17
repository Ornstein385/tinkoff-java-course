package edu.java.jdbc.dao;

import edu.java.jdbc.dto.ChatDto;
import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class JdbcChatDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcChatDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public void addChat(ChatDto chatDTO) {
        String sql = "INSERT INTO chats (id, created_at) VALUES (?, ?)";
        jdbcTemplate.update(sql, chatDTO.getId(), chatDTO.getCreatedAt());
    }

    @Transactional
    public void removeChat(Long chatId) {
        String sql = "DELETE FROM chats WHERE id = ?";
        jdbcTemplate.update(sql, chatId);
    }

    public List<ChatDto> findAllChats() {
        String sql = "SELECT * FROM chats";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new ChatDto(
            rs.getLong("id"),
            rs.getObject("created_at", OffsetDateTime.class)
        ));
    }
}
