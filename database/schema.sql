PRAGMA foreign_keys = ON;


CREATE TABLE IF NOT EXISTS user (
    userID INTEGER PRIMARY KEY,
    username TEXT,
    password TEXT,
    role TEXT
);


CREATE TABLE IF NOT EXISTS product (
    productID INTEGER PRIMARY KEY,
    name TEXT,
    description TEXT,
    price REAL,
    quantity INTEGER,
    ImagePath TEXT
);


CREATE TABLE IF NOT EXISTS inventory (
    inventoryID INTEGER PRIMARY KEY,
    totalProducts INTEGER
);


CREATE TABLE IF NOT EXISTS productDiscountType (
    discountID INTEGER PRIMARY KEY AUTOINCREMENT,
    code TEXT UNIQUE NOT NULL,
    discountType TEXT NOT NULL,
    discountValue REAL NOT NULL,
    active INTEGER DEFAULT 1,
    expirationDate TEXT
);

CREATE TABLE IF NOT EXISTS ProductDiscount (
    productID INTEGER,
    discountID INTEGER,
    PRIMARY KEY (productID, discountID),
    FOREIGN KEY (productID) REFERENCES product(productID) ON DELETE CASCADE,
    FOREIGN KEY (discountID) REFERENCES productDiscountType(discountID) ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS orders (
    orderID INTEGER PRIMARY KEY AUTOINCREMENT,
    orderDate DATE,
    customerName TEXT,
    totalAmount REAL,
    status TEXT
);


CREATE TABLE IF NOT EXISTS discount (
    discountCode TEXT PRIMARY KEY,
    expirationDate DATE
);