PRAGMA foreign_keys = ON;


CREATE TABLE IF NOT EXISTS user (
    userID INTEGER PRIMARY KEY,
    username TEXT,
    password TEXT,
    role TEXT
);


CREATE TABLE IF NOT EXISTS sale (
    saleID INTEGER PRIMARY KEY,
    saleName TEXT
);


CREATE TABLE IF NOT EXISTS product (
    productID INTEGER PRIMARY KEY,
    name TEXT,
    description TEXT,
    price REAL,
    quantity INTEGER,
    ImagePath TEXT,
    saleID INTEGER,

    FOREIGN KEY (saleID)
        REFERENCES sale(saleID)
);


CREATE TABLE IF NOT EXISTS inventory (
    inventoryID INTEGER PRIMARY KEY,
    totalProducts INTEGER
);


CREATE TABLE IF NOT EXISTS pricereduction (
    reductionID INTEGER PRIMARY KEY,
    reductionPercent REAL,
    active INTEGER
);


CREATE TABLE IF NOT EXISTS "order" (
    orderID INTEGER PRIMARY KEY,
    orderDate DATE,
    customerName TEXT,
    totalAmount REAL,
    status TEXT
);


CREATE TABLE IF NOT EXISTS discount (
    discountCode TEXT PRIMARY KEY,
    expirationDate DATE
);