CREATE DATABASE ISPG4NV1;
USE ISPG4NV1;



-- bang USERS
CREATE TABLE users (
    ID INT PRIMARY KEY IDENTITY(1,1),
    userName nVARCHAR(50) NOT NULL UNIQUE,
    userPassword nVARCHAR(100) NOT NULL,
	email nVARCHAR(30) NOT NULL UNIQUE,
    roleID int not null,
	[image] NVARCHAR(max) not null,
    createAt DATETIME not null DEFAULT GETDATE(),
	updateAt DATETIME,
	createBy int not null, -- ma cua user 
	isDelete bit DEFAULT 0, -- 1 la da xoa, 0 la chua xoa
	deleteAt DATETIME,
	deleteBy int
);

-- bang KHACH HANG
CREATE TABLE customers (
    customerID INT PRIMARY KEY IDENTITY(1,1),
    [name] nVARCHAR(30) NOT NULL,
    email nVARCHAR(30) NOT NULL UNIQUE,
    phone nVARCHAR(15),
	[address] nvarchar(100),
	totalDebt DECIMAL(18,2),
	createAt DATETIME not null DEFAULT GETDATE(),
	updateAt DATETIME,
	createBy int not null, -- ma cua user 
	isDelete bit default 0, -- 1 la da xoa, 0 la chua xoa
	deleteAt DATETIME,
	deleteBy int
);


-- bang ORDERS
CREATE TABLE orders (
    orderID INT PRIMARY KEY IDENTITY(1,1),
    customerID INT NOT NULL,
	userID int not null,
	totalAmount DECIMAL(18,2) not null CHECK (totalAmount >= 0),
	createAt DATETIME not null DEFAULT GETDATE(),
	updateAt DATETIME,
	createBy int not null, -- ma cua user 
	isDelete bit DEFAULT 0, -- 1 la da xoa, 0 la chua xoa
	deleteAt DATETIME,
	deleteBy int,
	porter int, -- so nguoi boc vac
	[status] nvarchar(255),
    FOREIGN KEY (customerID) REFERENCES customers(customerID),
    FOREIGN KEY (userID) REFERENCES users(ID)
);

-- bang ZONES
CREATE TABLE zones (
    zoneID INT PRIMARY KEY IDENTITY(1,1),
    zoneName nVARCHAR(100) NOT NULL,
    createAt DATETIME not null DEFAULT GETDATE(),
	updateAt DATETIME,
	createBy int not null, -- ma cua user 
	isDelete bit DEFAULT 0, -- 1 la da xoa, 0 la chua xoa
	deleteAt DATETIME,
	deleteBy int
);

-- bang PRODUCTs
CREATE TABLE products (
    productID INT PRIMARY KEY IDENTITY(1,1),
    productName nvarchar(100) not null,
	[description] nvarchar(max),
	price DECIMAL(18,2) not null CHECK (price >= 0),
	quantity int CHECK (quantity >= 0) not null DEFAULT 0, -- khi nao nhap_hang/ xuat hang thi quantity moi thay doi
	[image] nvarchar(max),
	createAt DATETIME not null DEFAULT GETDATE(),
	updateAt DATETIME,
	createBy int not null, -- ma cua user 
	isDelete bit DEFAULT 0, -- 1 la da xoa, 0 la chua xoa
	deleteAt DATETIME,
	deleteBy int 
);

CREATE TABLE ProductZones (
    productID INT NOT NULL,
    zoneID INT NOT NULL,
    PRIMARY KEY (productID, zoneID),
	createAt DATETIME not null DEFAULT GETDATE(),
	updateAt DATETIME,
	createBy int not null, -- ma cua user 
	isDelete bit DEFAULT 0, -- 1 la da xoa, 0 la chua xoa
	deleteAt DATETIME,
	deleteBy int,
    FOREIGN KEY (productID) REFERENCES products(productID),
    FOREIGN KEY (zoneID) REFERENCES zones(zoneID)
);

-- bang ORDERITEMS
CREATE TABLE OrderItems (
    orderitemID INT primary key IDENTITY(1,1),
    orderID INT NOT NULL,
	productID int not null,
	productName nvarchar(100) not null,
	price DECIMAL(18,2) not null CHECK (price >= 0),
	unitPrice DECIMAL(18,2) not null CHECK (unitPrice >= 0),  -- gia khi da discount
	quantity int CHECK (quantity >= 0) not null,
	[description] nvarchar(255),
    FOREIGN KEY (productID) REFERENCES products(productID),
    FOREIGN KEY (orderID) REFERENCES orders(orderID)
);


-- bang DEBT
CREATE TABLE DebtRecords (
    debtID INT PRIMARY KEY IDENTITY(1,1),
    customerID INT NOT NULL,
    orderID INT NOT NULL,
    amount DECIMAL(18,2) NOT NULL CHECK (amount >= 0),
	paymentStatus bit not null DEFAULT 0, -- 0 la no, 1 la tra no
	createAt DATETIME not null DEFAULT GETDATE(),
	updateAt DATETIME,
	createBy int not null, -- ma cua user 
	isDelete bit DEFAULT 0, -- 1 la da xoa, 0 la chua xoa
	deleteAt DATETIME,
	deleteBy int,
    FOREIGN KEY (customerID) REFERENCES customers(customerID)
);


