package edu.java.dto;

import java.time.OffsetDateTime;
import java.util.List;

public record StackOverflowQuestionResponse(List<Item> items, boolean hasMore, int quotaMax, int quotaRemaining) {

    public record Item(List<String> tags, Owner owner, boolean isAnswered, int viewCount, OffsetDateTime protectedDate,
                       Integer acceptedAnswerId, int answerCount, OffsetDateTime communityOwnedDate, int score,
                       OffsetDateTime lastActivityDate, OffsetDateTime creationDate, OffsetDateTime lastEditDate,
                       long questionId, String contentLicense, String link, String title) {

        public record Owner(int accountId, int reputation, int userId, String userType, Integer acceptRate,
                            String profileImage, String displayName, String link) {
        }
    }
}
