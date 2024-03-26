package edu.java.jooq.dao;

import edu.java.jooq.dto.ChatDto;
import edu.java.jooq.dto.LinkChatDto;
import edu.java.jooq.dto.LinkDto;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import static org.jooq.impl.DSL.currentOffsetDateTime;
import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;

@Repository
public class JooqLinkChatDao {

    private final DSLContext dsl;

    // Таблицы
    private static final org.jooq.Table<?> TABLE_LINK_CHAT = table("link_chat");
    private static final org.jooq.Table<?> TABLE_LINKS = table("links");
    private static final org.jooq.Table<?> TABLE_CHATS = table("chats");

    // Поля
    private static final org.jooq.Field<Long> FIELD_LINK_ID = field("link_id", Long.class);
    private static final org.jooq.Field<Long> FIELD_CHAT_ID = field("chat_id", Long.class);
    private static final org.jooq.Field<OffsetDateTime> FIELD_ADDED_AT = field("added_at", OffsetDateTime.class);

    // Имена полей для таблиц
    private static final String FIELD_NAME_ID = "id";
    private static final String FIELD_NAME_URL = "url";
    private static final String FIELD_NAME_LAST_UPDATED = "last_updated";
    private static final String FIELD_NAME_CREATED_AT = "created_at";

    @Autowired
    public JooqLinkChatDao(DSLContext dsl) {
        this.dsl = dsl;
    }

    @Transactional
    public void addLinkChat(LinkChatDto linkChatDto) {
        dsl.insertInto(TABLE_LINK_CHAT)
            .columns(FIELD_LINK_ID, FIELD_CHAT_ID)
            .values(linkChatDto.getLinkId(), linkChatDto.getChatId())
            .execute();
    }

    @Transactional
    public void removeLinkChat(Long linkId, Long chatId) {
        dsl.deleteFrom(TABLE_LINK_CHAT)
            .where(FIELD_LINK_ID.eq(linkId).and(FIELD_CHAT_ID.eq(chatId)))
            .execute();
    }

    @Transactional
    public List<LinkChatDto> findAllLinkChats() {
        return dsl.selectFrom(TABLE_LINK_CHAT)
            .fetch(chat -> new LinkChatDto(
                chat.getValue(FIELD_LINK_ID),
                chat.getValue(FIELD_CHAT_ID),
                chat.getValue(FIELD_ADDED_AT)
            ));
    }

    @Transactional
    public List<LinkDto> findAllLinksForChat(Long chatId) {
        return dsl.select(TABLE_LINKS.fields())
            .from(TABLE_LINK_CHAT)
            .join(TABLE_LINKS).on(FIELD_LINK_ID.eq(TABLE_LINKS.field(FIELD_NAME_ID, Long.class)))
            .where(FIELD_CHAT_ID.eq(chatId))
            .fetchInto(LinkDto.class);
    }

    @Transactional
    public List<LinkDto> findAllLinksForChat(Long chatId, long millisecondsBack, int limit) {
        return dsl.select(TABLE_LINKS.fields())
            .from(TABLE_LINK_CHAT)
            .join(TABLE_LINKS).on(FIELD_LINK_ID.eq(TABLE_LINKS.field(FIELD_NAME_ID, Long.class)))
            .where(FIELD_CHAT_ID.eq(chatId))
            .and(Objects.requireNonNull(TABLE_LINKS.field(FIELD_NAME_LAST_UPDATED, OffsetDateTime.class))
                .lessOrEqual(currentOffsetDateTime().minus(millisecondsBack)))
            .orderBy(TABLE_LINKS.field(FIELD_NAME_LAST_UPDATED, OffsetDateTime.class))
            .limit(limit)
            .fetchInto(LinkDto.class);
    }

    @Transactional
    public List<ChatDto> findAllChatsForLink(URI url) {
        return dsl.select(TABLE_CHATS.fields())
            .from(TABLE_LINK_CHAT)
            .join(TABLE_CHATS).on(FIELD_CHAT_ID.eq(TABLE_CHATS.field(FIELD_NAME_ID, Long.class)))
            .join(TABLE_LINKS).on(FIELD_LINK_ID.eq(TABLE_LINKS.field(FIELD_NAME_ID, Long.class)))
            .where(Objects.requireNonNull(TABLE_LINKS.field(FIELD_NAME_URL, String.class)).eq(url.toString()))
            .fetchInto(ChatDto.class);
    }

    @Transactional
    public List<ChatDto> findAllChatsForLink(URI url, long millisecondsBack, int limit) {
        return dsl.select(TABLE_CHATS.fields())
            .from(TABLE_LINK_CHAT)
            .join(TABLE_CHATS).on(FIELD_CHAT_ID.eq(TABLE_CHATS.field(FIELD_NAME_ID, Long.class)))
            .join(TABLE_LINKS).on(FIELD_LINK_ID.eq(TABLE_LINKS.field(FIELD_NAME_ID, Long.class)))
            .where(Objects.requireNonNull(TABLE_LINKS.field(FIELD_NAME_URL, String.class)).eq(url.toString()))
            .and(Objects.requireNonNull(TABLE_CHATS.field(FIELD_NAME_CREATED_AT, OffsetDateTime.class))
                .greaterOrEqual(currentOffsetDateTime().minus(millisecondsBack)))
            .orderBy(TABLE_CHATS.field(FIELD_NAME_CREATED_AT, OffsetDateTime.class))
            .limit(limit)
            .fetchInto(ChatDto.class);
    }
}

