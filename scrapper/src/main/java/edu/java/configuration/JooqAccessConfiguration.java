package edu.java.configuration;

import edu.java.jooq.dao.JooqChatDao;
import edu.java.jooq.dao.JooqLinkChatDao;
import edu.java.jooq.dao.JooqLinkDao;
import edu.java.jooq.service.JooqLinkService;
import edu.java.service.LinkService;
import javax.sql.DataSource;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jooq")
public class JooqAccessConfiguration {

    private final DataSource dataSource;

    @Autowired
    public JooqAccessConfiguration(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public DSLContext dslContext() {
        return DSL.using(dataSource, SQLDialect.POSTGRES);
    }

    @Bean
    public LinkService linkService(JooqLinkDao linkDao, JooqChatDao chatDao, JooqLinkChatDao linkChatDao) {
        return new JooqLinkService(linkDao, chatDao, linkChatDao);
    }
}
