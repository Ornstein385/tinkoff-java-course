package edu.java.bot.client;

import edu.java.bot.dto.api.request.AddLinkRequest;
import edu.java.bot.dto.api.request.RemoveLinkRequest;
import edu.java.bot.dto.api.response.LinkResponse;
import edu.java.bot.dto.api.response.ListLinksResponse;

public interface ScrapperClient {

    ListLinksResponse getAllLinks(Long tgChatId);

    LinkResponse addLink(Long tgChatId, AddLinkRequest addLinkRequest);

    String removeLink(Long tgChatId, RemoveLinkRequest removeLinkRequest);

    String registerChat(Long id);

    String deleteChat(Long id);
}
