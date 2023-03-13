DROP TABLE IF EXISTS cart;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS category;
DROP TABLE IF EXISTS supplier;
DROP TABLE IF EXISTS users;

CREATE TABLE category
(
    id          serial NOT NULL PRIMARY KEY,
    name        text   NOT NULL,
    department  text   NOT NULL,
    description text   NOT NULL
);

CREATE TABLE supplier
(
    id          serial NOT NULL PRIMARY KEY,
    name        text   NOT NULL,
    description text   NOT NULL
);

CREATE TABLE users
(
    id       serial NOT NULL PRIMARY KEY,
    name     text   NOT NULL,
    email    text   NOT NULL,
    password text   NOT NULL
);

CREATE TABLE product
(
    id           serial  NOT NULL PRIMARY KEY,
    category_id  INT,
    supplier_id  INT,
    name         text    NOT NULL,
    description  text    NOT NULL,
    defaultPrice NUMERIC NOT NULL,
    currency     text    NOT NULL,
    CONSTRAINT fk_category
        FOREIGN KEY (category_id)
            REFERENCES category (id),
    CONSTRAINT fk_supplier
        FOREIGN KEY (supplier_id)
            REFERENCES supplier (id)
);

CREATE TABLE orders
(
    id       serial      NOT NULL PRIMARY KEY,
    user_id  INT,
    date     timestamptz NOT NULL DEFAULT NOW(),
    status   text        NOT NULL DEFAULT 'CHECKED',
    quantity INT,
    total    NUMERIC,
    CONSTRAINT fk_user
        FOREIGN KEY (user_id)
            REFERENCES users (id)
);

CREATE TABLE cart
(
    id         serial NOT NULL PRIMARY KEY,
    product_id INT,
    order_id   INT,
    user_id    INT,
    CONSTRAINT fk_product
        FOREIGN KEY (product_id)
            REFERENCES product (id),
    CONSTRAINT fk_order
        FOREIGN KEY (order_id)
            REFERENCES orders (id),
    CONSTRAINT fk_user
        FOREIGN KEY (user_id)
            REFERENCES users (id)
);