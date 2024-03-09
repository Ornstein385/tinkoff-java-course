package edu.java.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import java.util.List;

public record StackOverflowQuestionResponse(
    @JsonProperty("items") List<Item> items,
    @JsonProperty("has_more") boolean hasMore,
    @JsonProperty("quota_max") int quotaMax,
    @JsonProperty("quota_remaining") int quotaRemaining) {

    public record Item(
        @JsonProperty("tags") List<String> tags,
        @JsonProperty("owner") Owner owner,
        @JsonProperty("is_answered") boolean isAnswered,
        @JsonProperty("view_count") int viewCount,
        @JsonProperty("protected_date") OffsetDateTime protectedDate,
        @JsonProperty("accepted_answer_id") Integer acceptedAnswerId,
        @JsonProperty("answer_count") int answerCount,
        @JsonProperty("community_owned_date") OffsetDateTime communityOwnedDate,
        @JsonProperty("score") int score,
        @JsonProperty("last_activity_date") OffsetDateTime lastActivityDate,
        @JsonProperty("creation_date") OffsetDateTime creationDate,
        @JsonProperty("last_edit_date") OffsetDateTime lastEditDate,
        @JsonProperty("question_id") long questionId,
        @JsonProperty("content_license") String contentLicense,
        @JsonProperty("link") String link,
        @JsonProperty("title") String title) {

        public record Owner(
            @JsonProperty("account_id") int accountId,
            @JsonProperty("reputation") int reputation,
            @JsonProperty("user_id") int userId,
            @JsonProperty("user_type") String userType,
            @JsonProperty("accept_rate") Integer acceptRate,
            @JsonProperty("profile_image") String profileImage,
            @JsonProperty("display_name") String displayName,
            @JsonProperty("link") String link) {
        }
    }
}
