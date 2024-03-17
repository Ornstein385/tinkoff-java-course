-- Создание таблицы для связи между ссылками и чатами
CREATE TABLE IF NOT EXISTS link_chat
(
    link_id          BIGINT NOT NULL,
    chat_id          BIGINT NOT NULL,
    added_at         TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (link_id, chat_id),
    FOREIGN KEY (link_id) REFERENCES links (id) ON DELETE CASCADE,
    FOREIGN KEY (chat_id) REFERENCES chats (id) ON DELETE CASCADE
);
