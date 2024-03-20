package edu.java.jooq.service;

import edu.java.model.Link;
import edu.java.service.LinkService;
import java.net.URI;
import java.util.Collection;

public class JooqLinkService implements LinkService {
    @Override
    public void add(long tgChatId) {

    }

    @Override
    public void add(long tgChatId, URI url) {

    }

    @Override
    public void remove(long tgChatId) {

    }

    @Override
    public void remove(long tgChatId, URI url) {

    }

    @Override
    public Collection<Link> listAllLinksForChat(long tgChatId) {
        return null;
    }

    @Override
    public Collection<Link> listAllLinksForChat(long tgChatId, long millisecondsBack, int limit) {
        return null;
    }

    @Override
    public Collection<Link> listAllLinks() {
        return null;
    }

    @Override
    public Collection<Link> listAllLinks(long millisecondsBack, int limit) {
        return null;
    }

    @Override
    public Collection<Long> listAllChatsForLink(URI url) {
        return null;
    }

    @Override
    public Collection<Long> listAllChatsForLink(URI url, long millisecondsBack, int limit) {
        return null;
    }

    @Override
    public void refreshLinkUpdateTime(URI url) {

    }
}
