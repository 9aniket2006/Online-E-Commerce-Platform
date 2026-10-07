-- ============================================
-- ONLINE E-COMMERCE PLATFORM
-- Oracle Database 11g XE
-- ============================================


-- ============================================
-- 1. USERS TABLE
-- ============================================

CREATE TABLE USERS (
    USER_ID NUMBER PRIMARY KEY,
    NAME VARCHAR2(100) NOT NULL,
    EMAIL VARCHAR2(100) UNIQUE NOT NULL,
    USER_PASSWORD VARCHAR2(100) NOT NULL,
    ROLE VARCHAR2(20) NOT NULL
);


-- ============================================
-- 2. PRODUCTS TABLE
-- ============================================

CREATE TABLE PRODUCTS (
    PRODUCT_ID NUMBER PRIMARY KEY,
    SELLER_ID NUMBER NOT NULL,
    PRODUCT_NAME VARCHAR2(100) NOT NULL,
    PRICE NUMBER(10,2) NOT NULL,
    STOCK NUMBER NOT NULL,
    CATEGORY VARCHAR2(50),

    CONSTRAINT FK_PRODUCT_SELLER
    FOREIGN KEY (SELLER_ID)
    REFERENCES USERS(USER_ID)
);


-- ============================================
-- 3. ORDERS TABLE
-- ============================================

CREATE TABLE ORDERS (
    ORDER_ID NUMBER PRIMARY KEY,
    BUYER_ID NUMBER NOT NULL,
    TOTAL_AMOUNT NUMBER(10,2) NOT NULL,
    STATUS VARCHAR2(30) DEFAULT 'Pending',
    ORDER_DATE DATE DEFAULT SYSDATE,

    CONSTRAINT FK_ORDER_BUYER
    FOREIGN KEY (BUYER_ID)
    REFERENCES USERS(USER_ID)
);


-- ============================================
-- 4. ORDER_ITEMS TABLE
-- ============================================

CREATE TABLE ORDER_ITEMS (
    ORDER_ITEM_ID NUMBER PRIMARY KEY,
    ORDER_ID NUMBER NOT NULL,
    PRODUCT_ID NUMBER NOT NULL,
    QUANTITY NUMBER NOT NULL,

    CONSTRAINT FK_ITEM_ORDER
    FOREIGN KEY (ORDER_ID)
    REFERENCES ORDERS(ORDER_ID),

    CONSTRAINT FK_ITEM_PRODUCT
    FOREIGN KEY (PRODUCT_ID)
    REFERENCES PRODUCTS(PRODUCT_ID)
);


-- ============================================
-- 5. CART TABLE
-- ============================================

CREATE TABLE CART (
    CART_ID NUMBER PRIMARY KEY,
    BUYER_ID NUMBER NOT NULL,
    PRODUCT_ID NUMBER NOT NULL,
    QUANTITY NUMBER NOT NULL,

    CONSTRAINT FK_CART_BUYER
    FOREIGN KEY (BUYER_ID)
    REFERENCES USERS(USER_ID),

    CONSTRAINT FK_CART_PRODUCT
    FOREIGN KEY (PRODUCT_ID)
    REFERENCES PRODUCTS(PRODUCT_ID)
);


-- ============================================
-- 6. SEQUENCES
-- ============================================

CREATE SEQUENCE CART_SEQ
START WITH 1
INCREMENT BY 1;

CREATE SEQUENCE ORDER_SEQ
START WITH 1
INCREMENT BY 1;

CREATE SEQUENCE ORDER_ITEM_SEQ
START WITH 1
INCREMENT BY 1;


-- ============================================
-- 7. SAMPLE USERS
-- ============================================

INSERT INTO USERS
(USER_ID, NAME, EMAIL, USER_PASSWORD, ROLE)
VALUES
(1, 'Admin User', 'admin@gmail.com', 'admin123', 'ADMIN');


INSERT INTO USERS
(USER_ID, NAME, EMAIL, USER_PASSWORD, ROLE)
VALUES
(2, 'Seller User', 'seller@gmail.com', 'seller123', 'SELLER');


INSERT INTO USERS
(USER_ID, NAME, EMAIL, USER_PASSWORD, ROLE)
VALUES
(3, 'Buyer User', 'buyer@gmail.com', 'buyer123', 'BUYER');


-- ============================================
-- 8. SAMPLE PRODUCTS
-- ============================================

INSERT INTO PRODUCTS
(PRODUCT_ID, SELLER_ID, PRODUCT_NAME, PRICE, STOCK, CATEGORY)
VALUES
(1, 2, 'Laptop', 55000, 10, 'Electronics');


INSERT INTO PRODUCTS
(PRODUCT_ID, SELLER_ID, PRODUCT_NAME, PRICE, STOCK, CATEGORY)
VALUES
(2, 2, 'Wireless Mouse', 800, 25, 'Electronics');


INSERT INTO PRODUCTS
(PRODUCT_ID, SELLER_ID, PRODUCT_NAME, PRICE, STOCK, CATEGORY)
VALUES
(3, 2, 'Keyboard', 1500, 15, 'Electronics');


INSERT INTO PRODUCTS
(PRODUCT_ID, SELLER_ID, PRODUCT_NAME, PRICE, STOCK, CATEGORY)
VALUES
(4, 2, 'Headphones', 2500, 20, 'Accessories');


-- ============================================
-- 9. SAVE CHANGES
-- ============================================

COMMIT;


-- ============================================
-- END OF DATABASE SCRIPT
-- ============================================