-- Storing suppliers
INSERT INTO supplier (name, description)
VALUES ('Amazon', 'Digital content and services');
INSERT INTO supplier (name, description)
VALUES ('Lenovo', 'Computers');
INSERT INTO supplier (name, description)
VALUES ('Apple', 'Computers');

-- Storing product categories
INSERT INTO category (name, department, description)
VALUES ('Tablet', 'Hardware', 'A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.');
INSERT INTO category (name, department, description)
VALUES ('Phone', 'Hardware', 'A mobile phone is a portable device for connecting to a telecommunications network in order to transmit and receive voice, video, or other data.');
INSERT INTO category (name, department, description)
VALUES ('Laptop', 'Hardware', 'Laptops are computers designed for portability. They have all the same components and capabilities of traditional desktops, but are smaller and can fold up.');

-- Storing tablet products
INSERT INTO product (category_id, supplier_id, name, description, defaultprice, currency)
VALUES (1, 1, 'Amazon Fire',
        'Fantastic price. Large content ecosystem. Good parental controls. Helpful technical support.',
        49.9, 'EUR');
INSERT INTO product (category_id, supplier_id, name, description, defaultprice, currency)
VALUES (1, 2, 'Lenovo IdeaPad Miix 700',
        'Keyboard cover is included. Fan-less Core m5 processor. Full-size USB ports. Adjustable kickstand.',
        479, 'EUR');
INSERT INTO product (category_id, supplier_id, name, description, defaultprice, currency)
VALUES (1, 1, 'Amazon Fire HD 8',
        'Amazon''s latest Fire HD 8 tablet is a great value for media consumption.',
        89, 'EUR');

-- Storing phone products
INSERT INTO product (category_id, supplier_id, name, description, defaultprice, currency)
VALUES (2, 3, 'iPhone X',
        'The 11th generation of the iPhone. Super Retina Display, TrueDepth Camera System, Face ID and A11 Bionic Chip with Neural Engine.',
        1000, 'EUR');
INSERT INTO product (category_id, supplier_id, name, description, defaultprice, currency)
VALUES (2, 3, 'iPhone 7',
        'The 10th generation of iPhone. Water and dust resistance, a new capacitive, static home button, revised antenna bands.',
        400, 'EUR');
INSERT INTO product (category_id, supplier_id, name, description, defaultprice, currency)
VALUES (2, 3, 'iPhone 11',
        'The 13th generation of iPhone. Addition of the more powerful Apple A13 Bionic chip as well as an ultra-wide dual-camera system.',
        700, 'EUR');

-- Storing laptop products
INSERT INTO product (category_id, supplier_id, name, description, defaultprice, currency)
VALUES (3, 3, 'Macbook Air 2020',
        'A macOS laptop with a 13.30-inch display that has a resolution of 2560x1600 pixels. It is powered by a Core i3 processor and it comes with 8GB of RAM.',
        2000, 'EUR');
INSERT INTO product (category_id, supplier_id, name, description, defaultprice, currency)
VALUES (3, 3, 'Macbook Pro 2019',
        'The 13-inch MacBook Pro is Apple''s best ultra-portable laptop, thanks to stylish looks, an excellent touchpad, and long battery life.',
        1400, 'EUR');
INSERT INTO product (category_id, supplier_id, name, description, defaultprice, currency)
VALUES (3, 3, 'Macbook 2015',
        'The 15-inch MacBook Pro has a brilliant Retina Display, powerful processor and new graphics card options, plus faster flash storage.',
        600, 'EUR');