package edu.java.bot.command;

import com.pengrad.telegrambot.model.BotCommand;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommandKeeper {
    private final HashMap<String, Command> commandHashMap = new HashMap<>();

    @Autowired
    public void setupCommands(List<Command> commands) {
        for (Command command : commands) {
            commandHashMap.put(command.getCommand(), command);
        }
    }

    public Command get(String command) {
        return commandHashMap.get(command);
    }

    public Command[] getAll() {
        return commandHashMap.values().toArray(new Command[0]);
    }

    public BotCommand[] getMenuCommands() {
        ArrayList<BotCommand> list = new ArrayList<>();
        for (Command command : commandHashMap.values()) {
            list.add(new BotCommand(command.getCommand(), command.getDescription()));
        }
        return list.toArray(new BotCommand[0]);
    }
}
