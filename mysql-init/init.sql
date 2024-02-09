CREATE TABLE IF NOT EXISTS owner
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    phone_number VARCHAR(255) NOT NULL UNIQUE,
    password     VARCHAR(255) NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;
;

CREATE TABLE IF NOT EXISTS product
(
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    owner_id        BIGINT       NOT NULL,
    category        VARCHAR(255) NOT NULL,
    price           DECIMAL      NOT NULL,
    cost            DECIMAL      NOT NULL,
    name            VARCHAR(255) NOT NULL,
    description     TEXT         NOT NULL,
    barcode         VARCHAR(255) NOT NULL,
    expiration_date DATE         NOT NULL,
    size            VARCHAR(255) NOT NULL,
    product_status  VARCHAR(255) NOT NULL,
    CONSTRAINT fk_product_owner FOREIGN KEY (owner_id) REFERENCES owner (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;
;

INSERT INTO owner (phone_number, password)
VALUES ('02012341234', '$2a$10$Hl0fukY71RY3AXiNN6OH8OkX5HFQwW6QQz3Bkc0m6iXHH5UoChK7m'),
       ('01012341234', '$2a$10$Hl0fukY71RY3AXiNN6OH8OkX5HFQwW6QQz3Bkc0m6iXHH5UoChK7m'),
       ('029415112', '$2a$10$Hl0fukY71RY3AXiNN6OH8OkX5HFQwW6QQz3Bkc0m6iXHH5UoChK7m'),
       ('01031235592', '$2a$10$Hl0fukY71RY3AXiNN6OH8OkX5HFQwW6QQz3Bkc0m6iXHH5UoChK7m')
ON DUPLICATE KEY UPDATE phone_number= VALUES(phone_number),
                        password    =VALUES(password);


INSERT INTO product (owner_id, category, price, cost, name, description, barcode, expiration_date, size, product_status)
VALUES (1, '음료', 3500, 1500, 'Latte', '크리미하고 풍부한 라떼', '1234567890123', '2023-12-31', 'LARGE', 'ACTIVE'),
       (1, '패스트리', 2000, 800, 'Croissant', '버터가 풍부하고 겹겹이 층이 있는 크루아상', '2345678901234', '2023-06-30', 'SMALL', 'ACTIVE'),
       (1, '스낵', 1500, 500, 'Bagel', '부드럽고 쫄깃한 베이글', '3456789012345', '2023-11-30', 'LARGE', 'ACTIVE'),
       (2, '음료', 4000, 2000, 'Cappuccino', '부드럽고 진한 카푸치노', '3456789012345', '2024-01-31', 'SMALL', 'ACTIVE'),
       (2, '스낵', 1500, 500, 'Bagel', '부드럽고 쫄깃한 베이글', '4567890123456', '2023-11-30', 'LARGE', 'ACTIVE'),
       (3, '음료', 3000, 1000, 'Americano', '깔끔하고 진한 아메리카노', '5678901234567', '2023-12-31', 'MEDIUM', 'ACTIVE'),
       (3, '패스트리', 2500, 1000, 'Muffin', '부드럽고 달콤한 머핀', '6789012345678', '2023-06-30', 'MEDIUM', 'ACTIVE'),
       (4, '음료', 3000, 500, 'Latte', '크리미하고 풍부한 라떼', '7890123456789', '2023-12-31', 'LARGE', 'ACTIVE'),
       (4, '패스트리', 2000, 800, 'Croissant', '버터가 풍부하고 겹겹이 층이 있는 크루아상', '8901234567890', '2023-06-30', 'SMALL', 'ACTIVE')