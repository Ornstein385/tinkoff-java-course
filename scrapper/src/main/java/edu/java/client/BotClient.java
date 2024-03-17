package edu.java.client;

import edu.java.dto.api.internal.request.LinkUpdateRequest;

public interface BotClient {

    String sendUpdate(LinkUpdateRequest linkUpdateRequest);
}
