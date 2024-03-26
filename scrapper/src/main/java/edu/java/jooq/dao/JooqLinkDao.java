package edu.java.jooq.dao;

import edu.java.jooq.dto.LinkDto;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;

@Repository
public class JooqLinkDao {

    private final DSLContext dsl;

    // Объекты для работы с таблицей и полями
    private static final org.jooq.Table<?> TABLE_LINKS = table("links");
    private static final org.jooq.Field<Long> FIELD_ID = field("id", Long.class);
    private static final org.jooq.Field<String> FIELD_URL = field("url", String.class);
    private static final org.jooq.Field<OffsetDateTime> FIELD_LAST_UPDATED =
        field("last_updated", OffsetDateTime.class);

    @Autowired
    public JooqLinkDao(DSLContext dsl) {
        this.dsl = dsl;
    }

    @Transactional
    public void addLink(LinkDto linkDTO) {
        dsl.insertInto(TABLE_LINKS)
            .set(FIELD_URL, linkDTO.getUrl())
            .set(FIELD_LAST_UPDATED, linkDTO.getLastUpdated())
            .execute();
    }

    @Transactional
    public void removeLink(String url) {
        dsl.deleteFrom(TABLE_LINKS)
            .where(FIELD_URL.eq(url))
            .execute();
    }

    @Transactional
    public Optional<LinkDto> getLink(String url) {
        return dsl.selectFrom(TABLE_LINKS)
            .where(FIELD_URL.eq(url))
            .fetchOptionalInto(LinkDto.class);
    }

    @Transactional
    public List<LinkDto> findAllLinks() {
        return dsl.selectFrom(TABLE_LINKS)
            .fetchInto(LinkDto.class);
    }

    @Transactional
    public List<LinkDto> findAllLinks(long millisecondsBack, int limit) {
        return dsl.selectFrom(TABLE_LINKS)
            .where(FIELD_LAST_UPDATED.lessOrEqual(DSL.currentOffsetDateTime().minus(millisecondsBack)))
            .orderBy(FIELD_LAST_UPDATED.asc())
            .limit(limit)
            .fetchInto(LinkDto.class);
    }

    @Transactional
    public void updateLinkLastUpdatedTime(String url) {
        dsl.update(TABLE_LINKS)
            .set(FIELD_LAST_UPDATED, DSL.currentOffsetDateTime())
            .where(FIELD_URL.eq(url))
            .execute();
    }
}
