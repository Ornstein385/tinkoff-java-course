/*
 * This file is generated by jOOQ.
 */
package edu.java.jooq.domain.tables.records;


import edu.java.jooq.domain.tables.LinkChat;

import java.beans.ConstructorProperties;
import java.time.OffsetDateTime;

import javax.annotation.processing.Generated;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.Field;
import org.jooq.Record2;
import org.jooq.Record3;
import org.jooq.Row3;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "https://www.jooq.org",
        "jOOQ version:3.18.9"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class LinkChatRecord extends UpdatableRecordImpl<LinkChatRecord> implements Record3<Long, Long, OffsetDateTime> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>LINK_CHAT.LINK_ID</code>.
     */
    public void setLinkId(@NotNull Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>LINK_CHAT.LINK_ID</code>.
     */
    @jakarta.validation.constraints.NotNull
    @NotNull
    public Long getLinkId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>LINK_CHAT.CHAT_ID</code>.
     */
    public void setChatId(@NotNull Long value) {
        set(1, value);
    }

    /**
     * Getter for <code>LINK_CHAT.CHAT_ID</code>.
     */
    @jakarta.validation.constraints.NotNull
    @NotNull
    public Long getChatId() {
        return (Long) get(1);
    }

    /**
     * Setter for <code>LINK_CHAT.ADDED_AT</code>.
     */
    public void setAddedAt(@Nullable OffsetDateTime value) {
        set(2, value);
    }

    /**
     * Getter for <code>LINK_CHAT.ADDED_AT</code>.
     */
    @Nullable
    public OffsetDateTime getAddedAt() {
        return (OffsetDateTime) get(2);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    @NotNull
    public Record2<Long, Long> key() {
        return (Record2) super.key();
    }

    // -------------------------------------------------------------------------
    // Record3 type implementation
    // -------------------------------------------------------------------------

    @Override
    @NotNull
    public Row3<Long, Long, OffsetDateTime> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    @Override
    @NotNull
    public Row3<Long, Long, OffsetDateTime> valuesRow() {
        return (Row3) super.valuesRow();
    }

    @Override
    @NotNull
    public Field<Long> field1() {
        return LinkChat.LINK_CHAT.LINK_ID;
    }

    @Override
    @NotNull
    public Field<Long> field2() {
        return LinkChat.LINK_CHAT.CHAT_ID;
    }

    @Override
    @NotNull
    public Field<OffsetDateTime> field3() {
        return LinkChat.LINK_CHAT.ADDED_AT;
    }

    @Override
    @NotNull
    public Long component1() {
        return getLinkId();
    }

    @Override
    @NotNull
    public Long component2() {
        return getChatId();
    }

    @Override
    @Nullable
    public OffsetDateTime component3() {
        return getAddedAt();
    }

    @Override
    @NotNull
    public Long value1() {
        return getLinkId();
    }

    @Override
    @NotNull
    public Long value2() {
        return getChatId();
    }

    @Override
    @Nullable
    public OffsetDateTime value3() {
        return getAddedAt();
    }

    @Override
    @NotNull
    public LinkChatRecord value1(@NotNull Long value) {
        setLinkId(value);
        return this;
    }

    @Override
    @NotNull
    public LinkChatRecord value2(@NotNull Long value) {
        setChatId(value);
        return this;
    }

    @Override
    @NotNull
    public LinkChatRecord value3(@Nullable OffsetDateTime value) {
        setAddedAt(value);
        return this;
    }

    @Override
    @NotNull
    public LinkChatRecord values(@NotNull Long value1, @NotNull Long value2, @Nullable OffsetDateTime value3) {
        value1(value1);
        value2(value2);
        value3(value3);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached LinkChatRecord
     */
    public LinkChatRecord() {
        super(LinkChat.LINK_CHAT);
    }

    /**
     * Create a detached, initialised LinkChatRecord
     */
    @ConstructorProperties({ "linkId", "chatId", "addedAt" })
    public LinkChatRecord(@NotNull Long linkId, @NotNull Long chatId, @Nullable OffsetDateTime addedAt) {
        super(LinkChat.LINK_CHAT);

        setLinkId(linkId);
        setChatId(chatId);
        setAddedAt(addedAt);
        resetChangedOnNotNull();
    }

    /**
     * Create a detached, initialised LinkChatRecord
     */
    public LinkChatRecord(edu.java.jooq.domain.tables.pojos.LinkChat value) {
        super(LinkChat.LINK_CHAT);

        if (value != null) {
            setLinkId(value.getLinkId());
            setChatId(value.getChatId());
            setAddedAt(value.getAddedAt());
            resetChangedOnNotNull();
        }
    }
}
