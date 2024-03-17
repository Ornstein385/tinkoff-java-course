package edu.java.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import java.util.List;

public record StackOverflowQuestionResponse(
    @JsonProperty("items") List<Item> items) {

    public record Item(
        @JsonProperty("owner") Owner owner,
        @JsonProperty("is_answered") boolean isAnswered,
        @JsonProperty("accepted_answer_id") Integer acceptedAnswerId,
        @JsonProperty("last_activity_date") OffsetDateTime lastActivityDate,
        @JsonProperty("creation_date") OffsetDateTime creationDate,
        @JsonProperty("last_edit_date") OffsetDateTime lastEditDate,
        @JsonProperty("question_id") long questionId) {

        public record Owner(
            @JsonProperty("account_id") int accountId,
            @JsonProperty("user_id") int userId) {
        }
    }
}
