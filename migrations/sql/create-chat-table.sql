-- Создание таблицы для хранения информации о чатах
CREATE TABLE IF NOT EXISTS chats
(
    id               BIGINT PRIMARY KEY,
    created_at       TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);
