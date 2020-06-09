delete FROM gross_domestic_product;

alter table gross_domestic_product
    drop primary key ;

alter table gross_domestic_product
    add quarter int not null check (quarter >= 1 && quarter <= 4);

alter table gross_domestic_product
    add primary key (year, quarter, type);
