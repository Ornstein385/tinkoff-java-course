package edu.java.bot.controllers;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetMyCommands;
import edu.java.bot.configuration.ApplicationConfig;
import edu.java.bot.dao.LinkDaoInterface;
import edu.java.bot.models.Link;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Bot {

    private final TelegramBot bot;
    private final ApplicationConfig config;
    private final LinkDaoInterface linkDao;
    private final HashSet<String> allowedDomains = new HashSet<>(List.of("github.com", "stackoverflow.com"));

    private static final String START = "/start";
    private static final String HELP = "/help";
    private static final String TRACK = "/track";
    private static final String UNTRACK = "/untrack";
    private static final String LIST = "/list";
    private static final String INCORRECT_LINK = "некорректный формат ссылки";
    private static final String NOT_SUPPORTED = "ресурс пока не поддерживается";

    @Autowired
    public Bot(ApplicationConfig config, LinkDaoInterface linkDao) {
        this.config = config;
        this.linkDao = linkDao;
        this.bot = new TelegramBot(config.getTelegramToken());
        setUpBot();
        setBotCommands();
    }

    private void setUpBot() {
        bot.setUpdatesListener(updates -> {
            for (Update update : updates) {
                if (update.message() != null) {
                    handleUpdate(update);
                }
            }
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }

    public void handleUpdate(Update update) {
        String text = update.message().text();
        long id = update.message().from().id();

        if (text.startsWith("/")) {
            executeCommand(id, text);
        } else {
            sendSimpleMessage(id, "нужно использовать одну из существующих команд");
        }
    }

    private void sendSimpleMessage(Long chatId, String message) {
        SendMessage request = new SendMessage(chatId, message);
        bot.execute(request);
    }

    private void executeCommand(Long chatId, String message) {
        Scanner scanner = new Scanner(message);
        String command = scanner.next();

        switch (command) {
            case START:
                sendSimpleMessage(chatId, "вы зарегистрированы");
                break;
            case HELP:
                executeHelpCommand(chatId);
                break;
            case TRACK:
                if (scanner.hasNext()) {
                    executeTrackCommand(chatId, scanner.next());
                } else {
                    sendSimpleMessage(chatId, INCORRECT_LINK);
                }
                break;
            case UNTRACK:
                if (scanner.hasNext()) {
                    executeUntrackCommand(chatId, scanner.next());
                } else {
                    sendSimpleMessage(chatId, INCORRECT_LINK);
                }
                break;
            case LIST:
                executeListCommand(chatId);
                break;
            default:
                sendSimpleMessage(chatId, "команда не существует");
                break;
        }
        scanner.close();
    }

    private void executeHelpCommand(Long chatId) {
        String text = """
            существуют следующие команды:
            /start - регистрация
            /help - все команды
            /track [url] - начать отслеживание ресурса
            /untrack [url] - прекратить отслеживание ресурса
            /list - показать все отслеживаемые ссылки
            """;

        sendSimpleMessage(chatId, text);
    }

    private void executeTrackCommand(Long chatId, String url) {
        Link link = new Link(chatId, url);
        if (allowedDomains.contains(link.getDomain())) {
            linkDao.add(link);
        } else {
            sendSimpleMessage(chatId, NOT_SUPPORTED);
        }
    }

    private void executeUntrackCommand(Long chatId, String url) {
        Link link = new Link(chatId, url);
        if (allowedDomains.contains(link.getDomain())) {
            linkDao.remove(link);
        } else {
            sendSimpleMessage(chatId, NOT_SUPPORTED);
        }
    }

    private void executeListCommand(Long chatId) {
        if (linkDao.getSize(chatId) == 0) {
            sendSimpleMessage(chatId, "нет отслеживаемых ссылок");
            return;
        }

        StringBuilder sb = new StringBuilder();
        int i = 1;
        for (Link link : linkDao.getAll(chatId)) {
            sb.append(i).append(".\n");
            sb.append(link.getUrl()).append("\n\n");
            i++;
        }
        sendSimpleMessage(chatId, sb.toString());
    }

    private void setBotCommands() {
        BotCommand[] commands = {
            new BotCommand(START, "регистрация"),
            new BotCommand(HELP, "все команды"),
            new BotCommand(TRACK, "начать отслеживание ресурса"),
            new BotCommand(UNTRACK, "прекратить отслеживание ресурса"),
            new BotCommand(LIST, "показать все отслеживаемые ссылки")
        };

        bot.execute(new SetMyCommands(commands));
    }
}
