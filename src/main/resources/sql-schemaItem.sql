DROP TABLE IF EXISTS `item_table`;

CREATE TABLE IF NOT EXISTS `item_table` (
    id int not null auto_increment,
name varchar(40) not null,
value decimal(5,2) not null,
primary key(id)
);