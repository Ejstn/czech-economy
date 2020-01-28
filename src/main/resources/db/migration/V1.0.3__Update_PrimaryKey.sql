alter table exchange_rate drop index exchange_rate_uk;

alter table exchange_rate modify id INT NOT NULL;

alter table exchange_rate drop primary key;

alter table exchange_rate drop id;

alter table exchange_rate add primary key (date, currency_code);




