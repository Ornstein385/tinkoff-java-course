package edu.java.jooq.service;

import edu.java.jooq.dao.JooqChatDao;
import edu.java.jooq.dao.JooqLinkChatDao;
import edu.java.jooq.dao.JooqLinkDao;
import edu.java.jooq.dto.ChatDto;
import edu.java.jooq.dto.LinkChatDto;
import edu.java.jooq.dto.LinkDto;
import edu.java.model.Link;
import edu.java.service.LinkService;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class JooqLinkService implements LinkService {

    private final JooqLinkDao linkDao;
    private final JooqChatDao chatDao;
    private final JooqLinkChatDao linkChatDao;

    @Autowired
    public JooqLinkService(JooqLinkDao linkDao, JooqChatDao chatDao, JooqLinkChatDao linkChatDao) {
        this.linkDao = linkDao;
        this.chatDao = chatDao;
        this.linkChatDao = linkChatDao;
    }

    @Override
    public void add(long tgChatId) {
        chatDao.addChat(new ChatDto(tgChatId, OffsetDateTime.now()));
    }

    @Override
    public void add(long tgChatId, URI url) {
        chatDao.addChat(new ChatDto(tgChatId, OffsetDateTime.now()));
        linkDao.addLink(new LinkDto(null, url.toString(), OffsetDateTime.now()));
        Long linkId = linkDao.getLink(url.toString())
                .orElseThrow(() -> new NoSuchElementException("URL not found when adding: " + url))
                .getId();
        linkChatDao.addLinkChat(new LinkChatDto(linkId, tgChatId, OffsetDateTime.now()));
    }

    @Override
    public void remove(long tgChatId) {
        chatDao.removeChat(tgChatId);
    }

    @Override
    public void remove(long tgChatId, URI url) {
        Long linkId = linkDao.getLink(url.toString())
                .orElseThrow(() -> new NoSuchElementException("URL not found when removing: " + url))
                .getId();
        linkChatDao.removeLinkChat(linkId, tgChatId);
    }

    @Override
    public Collection<Link> listAllLinksForChat(long tgChatId) {
        List<LinkDto> list = linkChatDao.findAllLinksForChat(tgChatId);
        return list.stream().map(dto -> new Link(URI.create(dto.getUrl()), dto.getLastUpdated()))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Link> listAllLinksForChat(long tgChatId, long millisecondsBack, int limit) {
        List<LinkDto> list = linkChatDao.findAllLinksForChat(tgChatId, millisecondsBack, limit);
        return list.stream().map(dto -> new Link(URI.create(dto.getUrl()), dto.getLastUpdated()))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Link> listAllLinks() {
        List<LinkDto> list = linkDao.findAllLinks();
        return list.stream().map(dto -> new Link(URI.create(dto.getUrl()), dto.getLastUpdated()))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Link> listAllLinks(long millisecondsBack, int limit) {
        List<LinkDto> list = linkDao.findAllLinks(millisecondsBack, limit);
        return list.stream().map(dto -> new Link(URI.create(dto.getUrl()), dto.getLastUpdated()))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Long> listAllChatsForLink(URI url) {
        List<ChatDto> list = linkChatDao.findAllChatsForLink(url);
        return list.stream().map(ChatDto::getId).collect(Collectors.toList());
    }

    @Override
    public Collection<Long> listAllChatsForLink(URI url, long millisecondsBack, int limit) {
        List<ChatDto> list = linkChatDao.findAllChatsForLink(url, millisecondsBack, limit);
        return list.stream().map(ChatDto::getId).collect(Collectors.toList());
    }

    @Override
    public void refreshLinkUpdateTime(URI url) {
        linkDao.updateLinkLastUpdatedTime(url.toString());
    }
}
