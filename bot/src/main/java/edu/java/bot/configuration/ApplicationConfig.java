package edu.java.bot.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class ApplicationConfig {

    //@NotEmpty
    @Value("${app.telegramToken}")
    private String telegramToken;

    //@NotEmpty
    @Value("${app.telegramName}")
    private String telegramName;
}
