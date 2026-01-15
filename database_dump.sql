CREATE DATABASE library_db;
USE library_db;

CREATE TABLE login(
id MEDIUMINT(8) NOT NULL AUTO_INCREMENT,
user_name VARCHAR(255) NOT NULL,
PASSWORD VARCHAR(100) NOT NULL,
user_type VARCHAR(100) NOT NULL,
PRIMARY KEY (id)
);
ALTER TABLE login MODIFY id INT NOT NULL AUTO_INCREMENT;
ALTER TABLE login
CHANGE PASSWORD password VARCHAR(100) NOT NULL;

ALTER TABLE login
MODIFY user_type ENUM('ADMIN', 'STUDENT') NOT NULL;

SELECT * FROM login;
-- Adding an Admin
INSERT INTO login (user_name, password, user_type)
VALUES ('admin123', 'pass123', 'ADMIN');

-- Adding a Student
INSERT INTO login (user_name, password, user_type)
VALUES ('student01', 'studpass', 'STUDENT');

CREATE TABLE books(
id MEDIUMINT(8) NOT NULL AUTO_INCREMENT,
sr_no INT(10) NOT NULL,
NAME VARCHAR(100) NOT NULL,
author_name VARCHAR(100) NOT NULL,
qty INT(2),
PRIMARY KEY (id)
);
ALTER TABLE books MODIFY id INT NOT NULL AUTO_INCREMENT;
ALTER TABLE books
CHANGE NAME name VARCHAR(100) NOT NULL;

ALTER TABLE books
DROP COLUMN sr_no;

ALTER TABLE books
DROP COLUMN qty;

ALTER TABLE books
ADD COLUMN total_qty INT DEFAULT 1,
ADD COLUMN available_qty INT DEFAULT 1;

CREATE TABLE students(
id MEDIUMINT(8) NOT NULL AUTO_INCREMENT,
std_name VARCHAR (255) NOT NULL,
reg_num VARCHAR(100) NOT NULL,
PRIMARY KEY (id)
);
ALTER TABLE students MODIFY id INT NOT NULL AUTO_INCREMENT;

ALTER TABLE students
MODIFY reg_num VARCHAR(100) NOT NULL UNIQUE;

ALTER TABLE students
ADD COLUMN email VARCHAR(100) UNIQUE,
ADD COLUMN contact VARCHAR(15);

CREATE TABLE booking_details (
id MEDIUMINT(8) NOT NULL AUTO_INCREMENT,
std_id MEDIUMINT(8) NOT NULL,
book_id MEDIUMINT(8) NOT NULL,
qty INT(2) NOT NULL,
PRIMARY KEY (id),
FOREIGN KEY (std_id) REFERENCES students(id),
FOREIGN KEY (book_id) REFERENCES books(id)
);
ALTER TABLE booking_details
DROP COLUMN qty;

ALTER TABLE booking_details
ADD COLUMN borrow_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
ADD COLUMN return_date TIMESTAMP NULL;

ALTER TABLE booking_details MODIFY std_id INT NOT NULL ;
ALTER TABLE booking_details MODIFY book_id INT NOT NULL ;

-- drop foreign key to alter data type of id in books and student
ALTER TABLE booking_details DROP FOREIGN KEY booking_details_ibfk_2;
ALTER TABLE booking_details DROP FOREIGN KEY booking_details_ibfk_1;

-- Re-add foreign key for book_id → books(id)
ALTER TABLE booking_details
ADD CONSTRAINT fk_book_id