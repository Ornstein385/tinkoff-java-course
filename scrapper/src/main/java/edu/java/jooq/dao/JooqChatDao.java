package edu.java.jooq.dao;

import edu.java.jooq.dto.ChatDto;
import java.time.OffsetDateTime;
import java.util.List;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;

@Repository
public class JooqChatDao {

    private final DSLContext dsl;

    // Объекты для работы с таблицей и полями
    private static final org.jooq.Table<?> TABLE_CHATS = table("chats");
    private static final org.jooq.Field<Long> FIELD_ID = field("id", Long.class);
    private static final org.jooq.Field<OffsetDateTime> FIELD_CREATED_AT =
        field("created_at", OffsetDateTime.class);

    @Autowired
    public JooqChatDao(DSLContext dsl) {
        this.dsl = dsl;
    }

    @Transactional
    public void addChat(ChatDto chatDTO) {
        dsl.insertInto(TABLE_CHATS)
            .set(FIELD_ID, chatDTO.getId())
            .execute();
    }

    @Transactional
    public void removeChat(Long chatId) {
        dsl.deleteFrom(TABLE_CHATS)
            .where(FIELD_ID.eq(chatId))
            .execute();
    }

    @Transactional
    public List<ChatDto> findAllChats() {
        return dsl.select(FIELD_ID, FIELD_CREATED_AT)
            .from(TABLE_CHATS)
            .fetch(r -> new ChatDto(
                r.getValue(FIELD_ID),
                r.getValue(FIELD_CREATED_AT)
            ));
    }
}

