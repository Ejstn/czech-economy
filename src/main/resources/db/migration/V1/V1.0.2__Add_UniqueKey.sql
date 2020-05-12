delete from exchange_rate;

alter table exchange_rate
    add constraint exchange_rate_uk
        unique (date, currency_code);
