CREATE SEQUENCE IF NOT EXISTS restaurant_seq START WITH 1 INCREMENT BY 1;

INSERT INTO restaurant (id, name, cuisine_type) VALUES (NEXT VALUE FOR restaurant_seq, 'Hola!', 'mexican');
INSERT INTO restaurant (id, name, cuisine_type) VALUES (NEXT VALUE FOR restaurant_seq, 'Uczta', 'polish');
INSERT INTO restaurant (id, name, cuisine_type) VALUES (NEXT VALUE FOR restaurant_seq, 'Bueno', 'italian');

CREATE SEQUENCE IF NOT EXISTS food_seq START WITH 1 INCREMENT BY 1;

INSERT INTO food (id, name, price, restaurant_id, is_dessert) VALUES (NEXT VALUE FOR food_seq, 'Tacos 1', 23.20, (select id from restaurant where cuisine_type = 'mexican'), false);
INSERT INTO food (id, name, price, restaurant_id, is_dessert) VALUES (NEXT VALUE FOR food_seq, 'Tacos 2', 21.50, (select id from restaurant where cuisine_type = 'mexican'), false);

INSERT INTO food (id, name, price, restaurant_id, is_dessert) VALUES (NEXT VALUE FOR food_seq, 'Pierogi', 19.50, (select id from restaurant where cuisine_type = 'polish'), false);
INSERT INTO food (id, name, price, restaurant_id, is_dessert) VALUES (NEXT VALUE FOR food_seq, 'Schabowy', 25.90, (select id from restaurant where cuisine_type = 'polish'), false);

INSERT INTO food (id, name, price, restaurant_id, is_dessert) VALUES (NEXT VALUE FOR food_seq, 'Pasta', 29.50, (select id from restaurant where cuisine_type = 'italian'), false);
INSERT INTO food (id, name, price, restaurant_id, is_dessert) VALUES (NEXT VALUE FOR food_seq, 'Pizza', 31.90, (select id from restaurant where cuisine_type = 'italian'), false);

INSERT INTO food (id, name, price, restaurant_id, is_dessert) VALUES (NEXT VALUE FOR food_seq, 'Cheesecake', 7.20, (select id from restaurant where cuisine_type = 'polish'), true);
INSERT INTO food (id, name, price, restaurant_id, is_dessert) VALUES (NEXT VALUE FOR food_seq, 'Chocolate ice cream', 6.30, (select id from restaurant where cuisine_type = 'italian'), true);

CREATE SEQUENCE IF NOT EXISTS drink_seq START WITH 1 INCREMENT BY 1;

INSERT INTO drink (id, name, price, contains_alco) VALUES (NEXT VALUE FOR drink_seq, 'Coca-Cola', 5.30, false);
INSERT INTO drink (id, name, price, contains_alco) VALUES (NEXT VALUE FOR drink_seq, 'Water', 3.10, false);
INSERT INTO drink (id, name, price, contains_alco) VALUES (NEXT VALUE FOR drink_seq, 'Tea', 4.50, false);
INSERT INTO drink (id, name, price, contains_alco) VALUES (NEXT VALUE FOR drink_seq, 'Beer', 6.00, true);
INSERT INTO drink (id, name, price, contains_alco) VALUES (NEXT VALUE FOR drink_seq, 'Wine', 7.00, true);

