CREATE TABLE IF NOT EXISTS owner
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    phoneNumber VARCHAR(255) NOT NULL UNIQUE,
    password    VARCHAR(255) NOT NULL
);


INSERT INTO owner (phoneNumber, password)
VALUES ('02012341234', '$2a$10$Hl0fukY71RY3AXiNN6OH8OkX5HFQwW6QQz3Bkc0m6iXHH5UoChK7m'),
       ('01012341234', '$2a$10$Hl0fukY71RY3AXiNN6OH8OkX5HFQwW6QQz3Bkc0m6iXHH5UoChK7m'),
       ('029415112', '$2a$10$Hl0fukY71RY3AXiNN6OH8OkX5HFQwW6QQz3Bkc0m6iXHH5UoChK7m'),
       ('01031235592', '$2a$10$Hl0fukY71RY3AXiNN6OH8OkX5HFQwW6QQz3Bkc0m6iXHH5UoChK7m')
ON DUPLICATE KEY UPDATE phoneNumber= VALUES (phoneNumber), password =VALUES(password);