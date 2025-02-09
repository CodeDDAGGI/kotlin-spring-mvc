CREATE DATABASE IF NOT EXISTS `payment`;
GRANT ALL ON `payment`.* TO 'user'@'%';
--  user가 grant라는 권한을 가짐

CREATE TABLE USER(
    id INT primary key  AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL ,
    email VARCHAR(100) UNIQUE ,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

SHOW BINARY LOGS;
