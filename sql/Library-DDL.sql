create database sampledb DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;

create table book (
  book_id INT AUTO_INCREMENT,
  book_name varchar(100) not null unique,
  author_name varchar(100),
  category varchar(100),
  price DOUBLE(8,2),
  quantity INT not null,
  created_date TIMESTAMP default current_timestamp not null,
  updated_date TIMESTAMP default current_timestamp not null,
  
  primary key (book_id)
);

create table user (
  user_id INT AUTO_INCREMENT,
  first_name varchar(100) not null,
  last_name varchar(100) not null,
  address1 varchar(100) not null,
  address2 varchar(100) not null,
  address3 varchar(100),
  city varchar(50) not null,
  state varchar(50) not null,
  postalcode varchar(50) not null,
  country varchar(50) not null,
  created_date TIMESTAMP default current_timestamp not null,
  updated_date TIMESTAMP default current_timestamp not null,

  primary key (user_id)
);

create table subscriptions (
  user_id INT,
  book_id INT,
  borrowed_date DATETIME default current_timestamp not null,
  due_date DATETIME DEFAULT NULL,
  returned_date DATETIME DEFAULT NULL,
  penalty_paid DOUBLE(8,2),
  FOREIGN KEY fk_subscriptions_user_id(user_id)
  REFERENCES user(user_id)
  ON UPDATE CASCADE
  ON DELETE RESTRICT,
  
  FOREIGN KEY fk_subscriptions_book_id(book_id)
  REFERENCES book(book_id)
  ON UPDATE CASCADE
  ON DELETE RESTRICT
);

create table book_inventory (
 book_id INT,
 qty_available INT, 
 FOREIGN KEY fk_book_inventory_book_id(book_id)
 REFERENCES book(book_id)
 ON UPDATE CASCADE
 ON DELETE RESTRICT
);
