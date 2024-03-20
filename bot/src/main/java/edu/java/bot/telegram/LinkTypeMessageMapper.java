package edu.java.bot.telegram;

import java.util.Map;

public class LinkTypeMessageMapper {
    private LinkTypeMessageMapper() {
    }

    private static Map<String, String> map = Map.of(
        "github_pull", "в github репозитории появился новый pull request: ",
        "github_commit", "в github репозитории появился новый коммит: ",
        "stackoverflow_answer", "в вопросе на stackoverflow появился новый ответ: ",
        "stackoverflow_comment", "в вопросе на stackoverflow появился новый комментарий: "
    );

    public static String getMessage(String messageType) {
        if (map.containsKey(messageType)) {
            return map.get(messageType);
        }
        return "для ресурса по ссылке появилось обновление";
    }
}
