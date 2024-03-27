package edu.java.jpa.repository;

import edu.java.jpa.model.ChatEntity;
import edu.java.jpa.model.LinkChatEntity;
import edu.java.jpa.model.LinkEntity;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface JpaLinkChatRepository extends JpaRepository<LinkChatEntity, LinkChatEntity.LinkChatId> {
    List<LinkChatEntity> findByChatId(Long chatId);

    List<LinkChatEntity> findByLinkUrl(String url);

    @Modifying
    @Transactional
    @Query("DELETE FROM LinkChatEntity lc WHERE lc.chat.id = :chatId AND lc.link.url = :url")
    void deleteByChatIdAndLinkUrl(@Param("chatId") Long chatId, @Param("url") String url);

    @Query("SELECT lc.link FROM LinkChatEntity lc WHERE lc.chat.id = :chatId")
    List<LinkEntity> findLinksByChatId(Long chatId);

    @Query("SELECT lc.chat FROM LinkChatEntity lc WHERE lc.link.url = :url")
    List<ChatEntity> findChatsByLinkUrl(String url);

    @Query("""
        SELECT lc.link FROM LinkChatEntity lc WHERE lc.chat.id = :chatId
        AND lc.link.lastUpdated <= CURRENT_TIMESTAMP - :millisecondsBack
        ORDER BY lc.link.lastUpdated ASC
        """)
    List<LinkEntity> findLinksForChatBefore(Long chatId, long millisecondsBack, Pageable pageable);

    @Query("""
        SELECT lc.chat FROM LinkChatEntity lc JOIN lc.link l WHERE l.url = :url
        AND lc.chat.createdAt >= CURRENT_TIMESTAMP - :millisecondsBack
        ORDER BY lc.chat.createdAt ASC
        """)
    List<ChatEntity> findChatsForLinkAfter(String url, long millisecondsBack, Pageable pageable);
}
