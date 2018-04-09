insert into sampledb.book (book_id, book_name, author_name, category, price, quantity, created_date) values
(1, 'C Programming', 'Richard Dennis', 'Computers', 20, 150, NOW());
insert into sampledb.book (book_id, book_name, author_name, category, price, quantity, created_date) values
(2, 'Math-1-1', 'Monica Rele', 'Educational', 25,  100, NOW());
insert into sampledb.book (book_id, book_name, author_name, category, price, quantity, created_date) values
(3, 'Walking with fear', 'Rod Jonson', 'Horror', 15, 110, NOW());

insert into sampledb.user(user_id, first_name, last_name, address1, address2, city, state, postalcode, country) values
(1,'David', 'Wood', '10 Winter St', 'Apt 210', 'Quincy', 'MA', '02169', 'US');
insert into sampledb.user(user_id, first_name, last_name, address1, address2, city, state, postalcode, country) values
(2,'Mary', 'Hicks', '10 Winter St', 'Apt 305', 'Quincy', 'MA', '02169', 'US');
insert into sampledb.user(user_id, first_name, last_name, address1, address2, city, state, postalcode, country) values
(3,'Minisha', 'Rupert', '15 Elm Street', 'Floor 3', 'Braintree', 'MA', '02184', 'US');

insert into sampledb.book_inventory () values (1,149);
insert into sampledb.book_inventory () values (2,99);
insert into sampledb.book_inventory () values (3,109);

insert into sampledb.subscriptions (user_id, book_id, borrowed_date, due_date) values (1, 2, NOW(), NOW() + INTERVAL 14 DAY);
insert into sampledb.subscriptions (user_id, book_id, borrowed_date, due_date) values (2, 3, NOW(), NOW() + INTERVAL 14 DAY);
insert into sampledb.subscriptions (user_id, book_id, borrowed_date, due_date) values (3, 1, NOW(), NOW() + INTERVAL 14 DAY);

commit;
