package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.client.ScrapperClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StartCommand implements Command {

    @Autowired
    public StartCommand(ScrapperClient scrapperClient) {
        this.scrapperClient = scrapperClient;
    }

    private ScrapperClient scrapperClient;

    @Override
    public String getCommand() {
        return "/start";
    }

    @Override
    public String getDescription() {
        return "регистрация";
    }

    @Override
    public SendMessage handle(Update update) {
        long id = update.message().from().id();
        scrapperClient.registerChat(id);
        return new SendMessage(id, "вы зарегистрированы");
    }
}
