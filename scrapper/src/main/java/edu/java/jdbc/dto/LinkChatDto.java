package edu.java.jdbc.dto;

import java.time.OffsetDateTime;

public class LinkChatDto {
    private Long linkId;
    private Long chatId;
    private OffsetDateTime addedAt;

    public LinkChatDto(Long linkId, Long chatId, OffsetDateTime addedAt) {
        this.linkId = linkId;
        this.chatId = chatId;
        this.addedAt = addedAt;
    }

    public Long getLinkId() {
        return linkId;
    }

    public Long getChatId() {
        return chatId;
    }

    public OffsetDateTime getAddedAt() {
        return addedAt;
    }

    public void setLinkId(Long linkId) {
        this.linkId = linkId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public void setAddedAt(OffsetDateTime addedAt) {
        this.addedAt = addedAt;
    }
}
