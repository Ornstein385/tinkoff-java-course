package edu.java.client;

import edu.java.dto.StackOverflowQuestionResponse;

public interface StackOverflowClient {
    StackOverflowQuestionResponse fetchQuestion(String questionId);
}
