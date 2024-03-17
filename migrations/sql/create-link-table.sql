-- Создание таблицы для хранения информации о ссылках
CREATE TABLE IF NOT EXISTS links
(
    id               BIGSERIAL PRIMARY KEY,
    url              TEXT NOT NULL UNIQUE,
    last_updated     TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);
