package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HelpCommand implements Command {

    private List<Command> commands;

    @Autowired
    public void setCommands(List<Command> commands) {
        this.commands = commands;
    }

    @Override
    public String getCommand() {
        return "/help";
    }

    @Override
    public String getDescription() {
        return "посмотреть описание всех команд";
    }

    @Override
    public SendMessage handle(Update update) {
        StringBuilder sb = new StringBuilder();
        for (Command command : commands) {
            sb.append(command.getCommand()).append(" -> ").append(command.getDescription()).append("\n");
        }
        return new SendMessage(update.message().from().id(), sb.toString());
    }
}
