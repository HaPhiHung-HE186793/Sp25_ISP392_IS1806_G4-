CREATE DATABASE ISPG4NV1;
USE ISPG4NV1;

-- Bảng Store
CREATE TABLE store ( 
    storeID INT PRIMARY KEY IDENTITY(1,1), 
    ownerID INT NOT NULL,
    storeName VARCHAR(255) NOT NULL, 
    address NVARCHAR(255) NOT NULL, 
    phone VARCHAR(20) NOT NULL, 
    email VARCHAR(255) UNIQUE NOT NULL,
    logostore NVARCHAR(MAX) NULL,
    createAt DATETIME DEFAULT GETDATE(), 
    updateAt DATETIME NULL, 
    createBy INT NOT NULL, 
    isDelete BIT DEFAULT 0, 
    deleteAt DATETIME NULL, 
    deleteBy INT NULL 
);

-- Bảng Users
CREATE TABLE users (
    ID INT PRIMARY KEY IDENTITY(1,1),
    userName NVARCHAR(50) NOT NULL UNIQUE,
    userPassword NVARCHAR(100) NOT NULL,
    email NVARCHAR(30) NOT NULL UNIQUE,
    roleID INT NOT NULL,
    [image] NVARCHAR(MAX) NOT NULL,
    storeID INT NULL,
    createAt DATETIME DEFAULT GETDATE(),
    updateAt DATETIME,
    createBy INT NOT NULL,
    isDelete BIT DEFAULT 0,
    deleteAt DATETIME,
    deleteBy INT,
    FOREIGN KEY (storeID) REFERENCES store(storeID)
);

-- Bảng Customers
CREATE TABLE customers (
    customerID INT PRIMARY KEY IDENTITY(1,1),
    [name] NVARCHAR(30) NOT NULL,
    email NVARCHAR(30) NOT NULL UNIQUE,
    phone NVARCHAR(15),
    [address] NVARCHAR(100),
    totalDebt DECIMAL(18,2) DEFAULT 0,
    storeID INT NULL,
    createAt DATETIME DEFAULT GETDATE(),
    updateAt DATETIME,
    createBy INT NOT NULL,
    isDelete BIT DEFAULT 0,
    deleteAt DATETIME,
    deleteBy INT,
    FOREIGN KEY (storeID) REFERENCES store(storeID)
);

-- Bảng Orders
CREATE TABLE orders (
    orderID INT PRIMARY KEY IDENTITY(1,1),
    customerID INT NOT NULL,
    userID INT NOT NULL,
    totalAmount DECIMAL(18,2) NOT NULL CHECK (totalAmount >= 0),
    storeID INT NULL,
    createAt DATETIME DEFAULT GETDATE(),
    updateAt DATETIME,
    createBy INT NOT NULL,
    isDelete BIT DEFAULT 0,
    deleteAt DATETIME,
    deleteBy INT,
    porter INT,
    [status] NVARCHAR(255),
    FOREIGN KEY (customerID) REFERENCES customers(customerID),
    FOREIGN KEY (userID) REFERENCES users(ID),
    FOREIGN KEY (storeID) REFERENCES store(storeID)
);

-- Bảng Zones
CREATE TABLE zones (
    zoneID INT PRIMARY KEY IDENTITY(1,1),
    zoneName NVARCHAR(100) NOT NULL,
    storeID INT NULL,
    createAt DATETIME DEFAULT GETDATE(),
    updateAt DATETIME,
    createBy INT NOT NULL,
    isDelete BIT DEFAULT 0,
    deleteAt DATETIME,
    deleteBy INT,
    FOREIGN KEY (storeID) REFERENCES store(storeID)
);

-- Bảng Products
CREATE TABLE products (
    productID INT PRIMARY KEY IDENTITY(1,1),
    productName NVARCHAR(100) NOT NULL,
    [description] NVARCHAR(MAX),
    price DECIMAL(18,2) NOT NULL CHECK (price >= 0),
    quantity INT DEFAULT 0 CHECK (quantity >= 0),
    [image] NVARCHAR(MAX),
    storeID INT NULL,
    createAt DATETIME DEFAULT GETDATE(),
    updateAt DATETIME,
    createBy INT NOT NULL,
    isDelete BIT DEFAULT 0,
    deleteAt DATETIME,
    deleteBy INT,
    FOREIGN KEY (storeID) REFERENCES store(storeID)
);

-- Bảng ProductUnits
CREATE TABLE ProductUnits (
    unitID INT PRIMARY KEY IDENTITY(1,1),
    productID INT NOT NULL,
    unitSize INT NOT NULL CHECK (unitSize > 0),
    FOREIGN KEY (productID) REFERENCES products(productID) ON DELETE CASCADE
);

-- Bảng ProductZones
CREATE TABLE ProductZones (
    productID INT NOT NULL,
    zoneID INT NOT NULL,
    storeID INT NULL,
    PRIMARY KEY (productID, zoneID),
    createAt DATETIME DEFAULT GETDATE(),
    updateAt DATETIME,
    createBy INT NOT NULL,
    isDelete BIT DEFAULT 0,
    deleteAt DATETIME,
    deleteBy INT,
    FOREIGN KEY (productID) REFERENCES products(productID),
    FOREIGN KEY (zoneID) REFERENCES zones(zoneID),
    FOREIGN KEY (storeID) REFERENCES store(storeID)
);

-- Bảng OrderItems
CREATE TABLE OrderItems (
    orderitemID INT PRIMARY KEY IDENTITY(1,1),
    orderID INT NOT NULL,
    productID INT NOT NULL,
    productName NVARCHAR(100) NOT NULL,
    price DECIMAL(18,2) NOT NULL CHECK (price >= 0),
    unitPrice DECIMAL(18,2) NOT NULL CHECK (unitPrice >= 0),
    quantity INT NOT NULL CHECK (quantity >= 0),
    [description] NVARCHAR(255),
    storeID INT NULL,
    FOREIGN KEY (productID) REFERENCES products(productID),
    FOREIGN KEY (orderID) REFERENCES orders(orderID),
    FOREIGN KEY (storeID) REFERENCES store(storeID)
);

-- Bảng DebtRecords
CREATE TABLE DebtRecords (
    debtID INT PRIMARY KEY IDENTITY(1,1),
    customerID INT NOT NULL,
    orderID INT NOT NULL,
    amount DECIMAL(18,2) NOT NULL CHECK (amount >= 0),
    paymentStatus BIT DEFAULT 0,
    description NVARCHAR(MAX) NULL,
    storeID INT NULL,
    createAt DATETIME DEFAULT GETDATE(),
    updateAt DATETIME,
    createBy INT NOT NULL,
    isDelete BIT DEFAULT 0,
    deleteAt DATETIME,
    deleteBy INT,
    [image] NVARCHAR(MAX),
    [status] INT,

    FOREIGN KEY (customerID) REFERENCES customers(customerID),
    FOREIGN KEY (storeID) REFERENCES store(storeID)
);
