package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.client.ScrapperClient;
import edu.java.bot.dto.api.response.LinkResponse;
import edu.java.bot.dto.api.response.ListLinksResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ListCommand implements Command {

    @Autowired
    public ListCommand(ScrapperClient scrapperClient) {
        this.scrapperClient = scrapperClient;
    }

    private ScrapperClient scrapperClient;

    @Override
    public String getCommand() {
        return "/list";
    }

    @Override
    public String getDescription() {
        return "показать все отслеживаемые ссылки";
    }

    @Override
    public SendMessage handle(Update update) {
        long id = update.message().from().id();
        ListLinksResponse listLinksResponse = scrapperClient.getAllLinks(id);
        if (listLinksResponse.getSize() == 0) {
            return new SendMessage(id, "нет отслеживаемых ссылок");
        }

        StringBuilder sb = new StringBuilder();
        int i = 1;
        for (LinkResponse linkResponse : listLinksResponse.getLinks()) {
            sb.append(i).append(".\n");
            sb.append(linkResponse.getUrl()).append("\n\n");
            i++;
        }
        return new SendMessage(id, sb.toString());
    }
}
