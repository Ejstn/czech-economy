CREATE TABLE average_salary
(
    quarter       INT   NOT NULL CHECK (quarter >= 1 && quarter <= 4),
    year          INT   NOT NULL CHECK (year >= 1),
    salary_crowns INT NOT NULL,
    PRIMARY KEY (year, quarter)
)