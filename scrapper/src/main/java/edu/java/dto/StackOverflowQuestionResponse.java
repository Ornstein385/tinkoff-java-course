package edu.java.dto;

import java.time.OffsetDateTime;
import java.util.List;
import lombok.Data;

@Data
public class StackOverflowQuestionResponse {

    private List<Item> items;
    private boolean hasMore;
    private int quotaMax;
    private int quotaRemaining;

    @Data
    public static class Item {
        private List<String> tags;
        private Owner owner;
        private boolean isAnswered;
        private int viewCount;
        private OffsetDateTime protectedDate;
        private Integer acceptedAnswerId;
        private int answerCount;
        private OffsetDateTime communityOwnedDate;
        private int score;
        private OffsetDateTime lastActivityDate;
        private OffsetDateTime creationDate;
        private OffsetDateTime lastEditDate;
        private long questionId;
        private String contentLicense;
        private String link;
        private String title;

        @Data
        public static class Owner {
            private int accountId;
            private int reputation;
            private int userId;
            private String userType;
            private Integer acceptRate;
            private String profileImage;
            private String displayName;
            private String link;

        }
    }
}
