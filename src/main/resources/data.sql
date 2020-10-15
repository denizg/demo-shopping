drop table if exists order_product;
drop table if exists shop_order;
drop table if exists product;
drop table if exists buyer;

CREATE TABLE product (
    id int(11) NOT NULL AUTO_INCREMENT,
    name varchar(255) NOT NULL,
    current_price int(11) NOT NULL,
    PRIMARY KEY (ID)
);

CREATE TABLE buyer (
   id int(11) NOT NULL AUTO_INCREMENT,
   email varchar(100) DEFAULT NULL,
   PRIMARY KEY (ID)
);

CREATE TABLE shop_order (
    id int(11) NOT NULL AUTO_INCREMENT,
    buyer_id int(11),
    order_date datetime NOT NULL,
    PRIMARY KEY (ID),
    CONSTRAINT buyer_fk FOREIGN KEY (buyer_id) REFERENCES buyer (id)
);

CREATE TABLE order_product (
    order_id int(11) NOT NULL,
    product_id int(11) NOT NULL,
    order_price int(11) NOT NULL,
    product_amount int(11) NOT NULL,
    PRIMARY KEY (order_id, product_id),
    UNIQUE KEY order_product_uk (order_id, product_id),
    CONSTRAINT order_fk1 FOREIGN KEY (order_id) REFERENCES shop_order (id),
    CONSTRAINT product_fk2 FOREIGN KEY (product_id) REFERENCES product (id)
);

commit;
--
-- insert into product (name, current_price) values ('product1', 1000);
-- insert into buyer(email) values ('denizyanik@gmail.com');
-- insert into shop_order (buyer_id, order_date) values (1, CURRENT_TIMESTAMP);