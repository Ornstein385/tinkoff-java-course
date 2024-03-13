package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;

@Component
public class StartCommand implements Command {

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
        return new SendMessage(id, "вы зарегистрированы");
    }
}
