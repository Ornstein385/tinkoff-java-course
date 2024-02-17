package edu.java.bot.models;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import lombok.Data;

@Data
public class Link {

    private long userId;

    private final String url;

    private final String domain;

    private Date lastCheck;

    private String parseLink(String url) {
        try {
            URL urlObject = new URL(url);
            return urlObject.getHost();
        } catch (MalformedURLException e) {
            return null;
        }
    }

    public Link(long userId, String url) {
        this.userId = userId;
        this.url = url;
        this.domain = parseLink(url);
    }
}
