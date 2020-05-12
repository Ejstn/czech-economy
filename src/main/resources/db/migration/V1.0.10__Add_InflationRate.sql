DROP TABLE IF EXISTS inflation_rate;

CREATE TABLE inflation_rate
(
    month  INT NOT NULL CHECK (month >= 1 && month <= 12),
    year INT NOT NULL CHECK (year >= 1),
    type ENUM (
        'THIS_YEAR_VS_LAST_YEAR',
        'THIS_MONTH_VS_PREVIOUS_YEARS_MONTH',
        'THIS_MONTH_VS_PREVIOUS_MONTH'),
    value_percent FLOAT NOT NULL,
    PRIMARY KEY (year, month, type)
);

