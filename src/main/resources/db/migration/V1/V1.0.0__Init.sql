CREATE TABLE exchange_rate
(
    id            BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    date DATE NOT NULL,
    currency_code VARCHAR(255) NOT NULL,
    currency_name VARCHAR(255) NOT NULL,
    amount        INT     NOT NULL,
    exchange_rate FLOAT   NOT NULL,
    country       VARCHAR(255) NOT NULL
);
