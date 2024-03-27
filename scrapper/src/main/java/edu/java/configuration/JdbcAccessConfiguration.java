package edu.java.configuration;

import edu.java.jdbc.dao.JdbcChatDao;
import edu.java.jdbc.dao.JdbcLinkChatDao;
import edu.java.jdbc.dao.JdbcLinkDao;
import edu.java.jdbc.service.JdbcLinkService;
import edu.java.service.LinkService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jdbc")
public class JdbcAccessConfiguration {
    @Bean
    public JdbcTemplate jdbcTemplate(ApplicationConfig applicationConfig) {
        return new JdbcTemplate(DataSourceBuilder.create().url(applicationConfig.dataSource().url())
            .username(applicationConfig.dataSource().username()).password(applicationConfig.dataSource().password())
            .driverClassName("org.postgresql.Driver").build());
    }

    @Bean
    public LinkService linkService(JdbcLinkDao linkDao, JdbcChatDao chatDao, JdbcLinkChatDao linkChatDao) {
        return new JdbcLinkService(linkDao, chatDao, linkChatDao);
    }
}
