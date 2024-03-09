package edu.java.bot.telegram;

import edu.java.bot.model.Link;
import java.util.HashSet;
import java.util.List;

public class AllowedDomainChecker {
    private static HashSet<String> allowedDomains = new HashSet<>(List.of("github.com", "stackoverflow.com"));

    //тут может быть более сложная проверка
    public static boolean isAllowed(Link link) {
        return link.getDomain() != null && allowedDomains.contains(link.getDomain());
    }

    private AllowedDomainChecker() {
    }
}
