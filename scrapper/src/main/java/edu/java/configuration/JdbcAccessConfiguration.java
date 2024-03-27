package edu.java.configuration;

import edu.java.jdbc.dao.JdbcChatDao;
import edu.java.jdbc.dao.JdbcLinkChatDao;
import edu.java.jdbc.dao.JdbcLinkDao;
import edu.java.jdbc.service.JdbcLinkService;
import edu.java.service.LinkService;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jdbc")
public class JdbcAccessConfiguration {

    private final DataSource dataSource;

    @Autowired
    public JdbcAccessConfiguration(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public LinkService linkService(JdbcLinkDao linkDao, JdbcChatDao chatDao, JdbcLinkChatDao linkChatDao) {
        return new JdbcLinkService(linkDao, chatDao, linkChatDao);
    }
}
