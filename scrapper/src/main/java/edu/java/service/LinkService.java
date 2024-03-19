package edu.java.service;

import edu.java.model.Link;
import java.net.URI;
import java.util.Collection;

public interface LinkService {
    void add(long tgChatId);

    void add(long tgChatId, URI url);

    void remove(long tgChatId);

    void remove(long tgChatId, URI url);

    Collection<Link> listAllLinksForChat(long tgChatId);

    Collection<Link> listAllLinksForChat(long tgChatId, long millisecondsBack, int limit);

    Collection<Link> listAllLinks();

    Collection<Link> listAllLinks(long millisecondsBack, int limit);

    Collection<Long> listAllChatsForLink(URI url);

    Collection<Long> listAllChatsForLink(URI url, long millisecondsBack, int limit);

    void refreshLinkUpdateTime(URI url);
}
