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
    id int not null auto_increment,
name varchar(40) not null,
value decimal(5,2) not null,
primary key(id)
);





create table IF NOT EXISTS `order_table`(
id int not null auto_increment,
customer_id int ,

primary key(id),
foreign key(customer_id) references customer_table(id)  

);

create table IF NOT EXISTS `order_item`(

order_id int,
item_id int ,
quantity int,
foreign key(order_id) references order_table(id) ,
foreign key(item_id) references item_table(id) 
);

