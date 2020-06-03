delete from unemployment_rate;

alter table unemployment_rate
    add month INT NOT NULL CHECK (month >= 1 && month <= 12);

alter table  unemployment_rate drop primary key;

alter table unemployment_rate drop column quarter;

alter table unemployment_rate
    add primary key (year, month);