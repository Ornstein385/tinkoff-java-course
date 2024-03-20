/*
 * This file is generated by jOOQ.
 */
package edu.java.jooq.domain.tables;


import edu.java.jooq.domain.DefaultSchema;
import edu.java.jooq.domain.Keys;
import edu.java.jooq.domain.tables.records.LinkChatRecord;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import javax.annotation.processing.Generated;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Function3;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Records;
import org.jooq.Row3;
import org.jooq.Schema;
import org.jooq.SelectField;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


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
public class LinkChat extends TableImpl<LinkChatRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>LINK_CHAT</code>
     */
    public static final LinkChat LINK_CHAT = new LinkChat();

    /**
     * The class holding records for this type
     */
    @Override
    @NotNull
    public Class<LinkChatRecord> getRecordType() {
        return LinkChatRecord.class;
    }

    /**
     * The column <code>LINK_CHAT.LINK_ID</code>.
     */
    public final TableField<LinkChatRecord, Long> LINK_ID = createField(DSL.name("LINK_ID"), SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>LINK_CHAT.CHAT_ID</code>.
     */
    public final TableField<LinkChatRecord, Long> CHAT_ID = createField(DSL.name("CHAT_ID"), SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>LINK_CHAT.ADDED_AT</code>.
     */
    public final TableField<LinkChatRecord, OffsetDateTime> ADDED_AT = createField(DSL.name("ADDED_AT"), SQLDataType.TIMESTAMPWITHTIMEZONE(6).nullable(false).defaultValue(DSL.field(DSL.raw("CURRENT_TIMESTAMP"), SQLDataType.TIMESTAMPWITHTIMEZONE)), this, "");

    private LinkChat(Name alias, Table<LinkChatRecord> aliased) {
        this(alias, aliased, null);
    }

    private LinkChat(Name alias, Table<LinkChatRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>LINK_CHAT</code> table reference
     */
    public LinkChat(String alias) {
        this(DSL.name(alias), LINK_CHAT);
    }

    /**
     * Create an aliased <code>LINK_CHAT</code> table reference
     */
    public LinkChat(Name alias) {
        this(alias, LINK_CHAT);
    }

    /**
     * Create a <code>LINK_CHAT</code> table reference
     */
    public LinkChat() {
        this(DSL.name("LINK_CHAT"), null);
    }

    public <O extends Record> LinkChat(Table<O> child, ForeignKey<O, LinkChatRecord> key) {
        super(child, key, LINK_CHAT);
    }

    @Override
    @Nullable
    public Schema getSchema() {
        return aliased() ? null : DefaultSchema.DEFAULT_SCHEMA;
    }

    @Override
    @NotNull
    public UniqueKey<LinkChatRecord> getPrimaryKey() {
        return Keys.CONSTRAINT_47;
    }

    @Override
    @NotNull
    public List<ForeignKey<LinkChatRecord, ?>> getReferences() {
        return Arrays.asList(Keys.CONSTRAINT_470, Keys.CONSTRAINT_4702);
    }

    private transient Links _links;
    private transient Chats _chats;

    /**
     * Get the implicit join path to the <code>PUBLIC.LINKS</code> table.
     */
    public Links links() {
        if (_links == null)
            _links = new Links(this, Keys.CONSTRAINT_470);

        return _links;
    }

    /**
     * Get the implicit join path to the <code>PUBLIC.CHATS</code> table.
     */
    public Chats chats() {
        if (_chats == null)
            _chats = new Chats(this, Keys.CONSTRAINT_4702);

        return _chats;
    }

    @Override
    @NotNull
    public LinkChat as(String alias) {
        return new LinkChat(DSL.name(alias), this);
    }

    @Override
    @NotNull
    public LinkChat as(Name alias) {
        return new LinkChat(alias, this);
    }

    @Override
    @NotNull
    public LinkChat as(Table<?> alias) {
        return new LinkChat(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    @NotNull
    public LinkChat rename(String name) {
        return new LinkChat(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    @NotNull
    public LinkChat rename(Name name) {
        return new LinkChat(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    @NotNull
    public LinkChat rename(Table<?> name) {
        return new LinkChat(name.getQualifiedName(), null);
    }

    // -------------------------------------------------------------------------
    // Row3 type methods
    // -------------------------------------------------------------------------

    @Override
    @NotNull
    public Row3<Long, Long, OffsetDateTime> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Function)}.
     */
    public <U> SelectField<U> mapping(Function3<? super Long, ? super Long, ? super OffsetDateTime, ? extends U> from) {
        return convertFrom(Records.mapping(from));
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Class,
     * Function)}.
     */
    public <U> SelectField<U> mapping(Class<U> toType, Function3<? super Long, ? super Long, ? super OffsetDateTime, ? extends U> from) {
        return convertFrom(toType, Records.mapping(from));
    }
}
