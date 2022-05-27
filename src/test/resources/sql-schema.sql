DROP database testims;
CREATE database testims;
use testims;

DROP TABLE IF EXISTS `customer_table`;
DROP TABLE IF exists `order_item`;
DROP TABLE IF EXISTS `order_table`;
DROP TABLE IF EXISTS `item_table`;


CREATE TABLE IF NOT EXISTS `customer_table` (
    `id` INT(11) NOT NULL AUTO_INCREMENT,
    `first_name` VARCHAR(40) DEFAULT NULL,
    `surname` VARCHAR(40) DEFAULT NULL,
    PRIMARY KEY (`id`)
);


CREATE TABLE IF NOT EXISTS `item_table` (
id int NOT NULL auto_increment,
name varchar(40) NOT NULL,
value decimal(5,2) NOT NULL,
PRIMARY KEY(id)
);


CREATE TABLE IF NOT EXISTS `order_table`(
id int NOT NULL auto_increment,
customer_id int ,
PRIMARY KEY(id),
FOREIGN KEY(customer_id) 
REFERENCES customer_table(id)  

);

CREATE TABLE IF NOT EXISTS `order_item`(
order_id int,
item_id int ,
quantity int,
FOREIGN KEY(order_id) 
REFERENCES order_table(id) ,
FOREIGN KEY(item_id)
REFERENCES item_table(id) 
);

