package edu.java.jdbc.service;

import edu.java.jdbc.dao.JdbcChatDao;
import edu.java.jdbc.dao.JdbcLinkChatDao;
import edu.java.jdbc.dao.JdbcLinkDao;
import edu.java.jdbc.dto.ChatDto;
import edu.java.jdbc.dto.LinkChatDto;
import edu.java.jdbc.dto.LinkDto;
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
public class JdbcLinkService implements LinkService {
    JdbcLinkDao linkDao;
    JdbcChatDao chatDao;
    JdbcLinkChatDao linkChatDao;

    @Autowired
    public JdbcLinkService(JdbcLinkDao linkDao, JdbcChatDao chatDao, JdbcLinkChatDao linkChatDao) {
        this.linkDao = linkDao;
        this.chatDao = chatDao;
        this.linkChatDao = linkChatDao;
    }

    @Override
    public void add(long tgChatId) {
        chatDao.addChat(new ChatDto(tgChatId, null));
    }

    @Override
    public void add(long tgChatId, URI url) {
        chatDao.addChat(new ChatDto(tgChatId, null));
        linkDao.addLink(new LinkDto(null, url.toString(), OffsetDateTime.now()));
        Long linkId = linkDao.getLink(url.toString())
            .orElseThrow(() -> new NoSuchElementException("не был найден URL при добавлении: " + url))
            .getId();
        linkChatDao.addLinkChat(new LinkChatDto(linkId, tgChatId, null));
    }

    @Override
    public void remove(long tgChatId) {
        chatDao.removeChat(tgChatId);
    }

    @Override
    public void remove(long tgChatId, URI url) {
        Long linkId = linkDao.getLink(url.toString())
            .orElseThrow(() -> new NoSuchElementException("не был найден URL при удалении: " + url))
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
