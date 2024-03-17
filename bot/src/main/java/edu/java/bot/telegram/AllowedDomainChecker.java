package edu.java.bot.telegram;

import edu.java.bot.models.Link;
import java.util.HashSet;
import java.util.List;

public class AllowedDomainChecker {
    private static HashSet<String> allowedDomains = new HashSet<>(List.of("github.com", "stackoverflow.com"));

    //тут может быть более сложная проверка
    public static boolean isAllowed(Link link) {
        if (link.getDomain() != null && allowedDomains.contains(link.getDomain())) {
            return true;
        }
        return false;
    }

    private AllowedDomainChecker() {
    }
}
