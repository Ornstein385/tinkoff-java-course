package edu.java.configuration;

import edu.java.jooq.dao.JooqChatDao;
import edu.java.jooq.dao.JooqLinkChatDao;
import edu.java.jooq.dao.JooqLinkDao;
import edu.java.jooq.service.JooqLinkService;
import edu.java.service.LinkService;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jooq")
public class JooqAccessConfiguration {
    @Bean
    public DSLContext dslContext(ApplicationConfig applicationConfig) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource(
            applicationConfig.dataSource().url(),
            applicationConfig.dataSource().username(),
            applicationConfig.dataSource().password()
        );
        dataSource.setDriverClassName("org.postgresql.Driver");
        return DSL.using(dataSource, SQLDialect.POSTGRES);
    }

    @Bean
    public LinkService linkService(JooqLinkDao linkDao, JooqChatDao chatDao, JooqLinkChatDao linkChatDao) {
        return new JooqLinkService(linkDao, chatDao, linkChatDao);
    }
}
