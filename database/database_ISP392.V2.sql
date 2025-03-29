CREATE DATABASE ISPG4NV1;
USE ISPG4NV1;




CREATE TABLE schedule ( 
scheduleID INT PRIMARY KEY IDENTITY(1,1), 
scheduleName nVARCHAR(255) NOT NULL, 
startDate TIME(0) not NULL,
endDate TIME(0) not NULL,
breakStart time(0) not null,
breakEnd time(0) not null ,
workDuration AS FORMAT(DATEADD(MINUTE, DATEDIFF(MINUTE, startDate, endDate), '00:00'), 'HH\:mm'),
createAt DATETIME not null DEFAULT GETDATE(), 
updateAt DATETIME NULL, 
createBy INT not NULL, 
isDelete INT DEFAULT 0, 
deleteAt DATETIME NULL, 
deleteBy INT NULL,
storeid int not null
);

ALTER TABLE ProductZones 
ADD COLUMN navigation nVARCHAR(255) DEFAULT '',
ADD COLUMN quantity INT DEFAULT 0;




CREATE TABLE ProductPriceHistory (
    historyID INT PRIMARY KEY IDENTITY(1,1),
    productID INT NOT NULL,
    price DECIMAL(18,2) NOT NULL, -- Giá mới (có thể là giá nhập hoặc giá bán)
    priceType NVARCHAR(10) NOT NULL CHECK (priceType IN ('import', 'sell')), -- Loại giá (nhập/bán)
    changedAt DATETIME NOT NULL DEFAULT GETDATE(), -- Thời điểm thay đổi
    changedBy INT NOT NULL, -- Người thực hiện thay đổi
    FOREIGN KEY (productID) REFERENCES products(productID) ON DELETE CASCADE,
    FOREIGN KEY (changedBy) REFERENCES users(ID)
);
ALTER TABLE products ADD importPrice DECIMAL(18,2) NULL;
ALTER TABLE ProductPriceHistory ADD storeID INT NULL;
ALTER TABLE ProductPriceHistory ADD CONSTRAINT fk_productpricehistory_store FOREIGN KEY (storeID) REFERENCES store(storeID);

CREATE TABLE ProductUnits (
    unitID INT PRIMARY KEY IDENTITY(1,1),
    productID INT NOT NULL,
    unitSize INT NOT NULL CHECK (unitSize > 0),  -- Lưu đơn vị bao dưới dạng số kg
    CONSTRAINT fk_product_unit FOREIGN KEY (productID) REFERENCES products(productID) ON DELETE CASCADE
);


CREATE TABLE store ( 
storeID INT PRIMARY KEY IDENTITY(1,1), 
storeName nVARCHAR(255) NOT NULL, 
address NVARCHAR(255) NOT NULL, 
phone nVARCHAR(20) NOT NULL, 
email VARCHAR(255) UNIQUE NOT NULL,
logostore nvarchar(max) NULL,
createAt DATETIME not null DEFAULT GETDATE(), 
updateAt DATETIME NULL, 
createBy INT not NULL, 
isDelete INT DEFAULT 0, 
deleteAt DATETIME NULL, 
deleteBy INT NULL 
);


-- Thông tin người dùng
CREATE TABLE users (
    userID INT PRIMARY KEY IDENTITY(1,1),
    userName nVARCHAR(50) NOT NULL UNIQUE,
    userPassword nVARCHAR(100) NOT NULL,
    roleID int not null,
    createAt DATETIME not null DEFAULT GETDATE(),
    updateAt DATETIME,
    createBy int not null, -- ma cua user 
    isDelete bit DEFAULT 0, -- 1 la da xoa, 0 la chua xoa
    deleteAt DATETIME,
    deleteBy int,
    FOREIGN KEY (roleID) REFERENCES roles(roleID)
);

-- Thông tin khách hàng
CREATE TABLE customers (
    customerID INT PRIMARY KEY IDENTITY(1,1),
    [name] nVARCHAR(30) NOT NULL,
    email nVARCHAR(30) NOT NULL UNIQUE,
    phone nVARCHAR(15),
    [address] nvarchar(100),
    createAt DATETIME not null DEFAULT GETDATE(),
    updateAt DATETIME DEFAULT GETDATE(),
    createBy int not null, -- ma cua user 
    isDelete bit default 0, -- 1 la da xoa, 0 la chua xoa
    deleteAt DATETIME,
    deleteBy int
);


-- Thông tin đơn xuất/nhập gạo
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
    [status] nvarchar(255),
    FOREIGN KEY (customerID) REFERENCES customers(customerID),
    FOREIGN KEY (userID) REFERENCES users(userID)
);

-- Thông tin khu vực
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

-- Thông tin gạo 
CREATE TABLE products (
    productID INT PRIMARY KEY IDENTITY(1,1),
    productName nvarchar(100) not null,
    [description] nvarchar(max),
    price DECIMAL(18,2) not null CHECK (price >= 0),
    quantity int CHECK (quantity >= 0) not null,
    zoneID int not null,
    createAt DATETIME not null DEFAULT GETDATE(),
    updateAt DATETIME,
    createBy int not null, -- ma cua user 
    isDelete bit DEFAULT 0, -- 1 la da xoa, 0 la chua xoa
    deleteAt DATETIME,
    deleteBy int ,
    FOREIGN KEY (zoneID) REFERENCES zones(zoneID)
);

-- Thông tin chi tiết đơn xuất/nhập gạo
CREATE TABLE OrderItems (
    orderitemID INT primary key IDENTITY(1,1),
    orderID INT NOT NULL,
    productID int not null,
    price DECIMAL(18,2) not null CHECK (price >= 0),
    unitPrice DECIMAL(18,2) not null,
    quantity int CHECK (quantity >= 0) not null,
    [description] nvarchar(255),
    FOREIGN KEY (productID) REFERENCES products(productID),
    FOREIGN KEY (orderID) REFERENCES orders(orderID)
);





-- Thông tin ghi nợ
CREATE TABLE DebtRecords (
    debtID INT PRIMARY KEY IDENTITY(1,1),
    customerID INT NOT NULL,
    orderID INT NOT NULL,
    amount DECIMAL(18,2) NOT NULL CHECK (amount >= 0),
    paymentStatus int not null DEFAULT 0, -- 0 la vay no, 1 la tra no, 2 la di vay, 3 la di tra
    createAt DATETIME not null DEFAULT GETDATE(),
    updateAt DATETIME  DEFAULT GETDATE(),
    createBy int not null, -- ma cua user 
    isDelete bit DEFAULT 0, -- 1 la da xoa, 0 la chua xoa
    deleteAt DATETIME,
    deleteBy int,
    img nvarchar(max);
    status int;
    FOREIGN KEY (customerID) REFERENCES customers(customerID)
);


ALTER TABLE orders
ADD paidAmount DECIMAL(18,2)  CHECK (paidAmount >= 0),
    orderType BIT  CHECK (orderType IN (0,1)); -- 0 la nhap, 1 la xuat
ALTER TABLE customers
ALTER COLUMN email NVARCHAR(30) NULL;


ALTER TABLE OrderItems DROP CONSTRAINT FK__OrderItem__produ__34C8D9D1;

drop table productZones
ALTER TABLE Zones 
ADD COLUMN productID int;
ALTER TABLE Zones ADD CONSTRAINT fk_zones_products FOREIGN KEY (productID) REFERENCES products(productID);
ALTER TABLE Zones  ADD  image nVARCHAR(255);
ALTER TABLE Zones  ADD  description nVARCHAR(255);
ALTER TABLE customers ADD storeID INT NULL;
ALTER TABLE DebtRecords ADD storeID INT NULL;
ALTER TABLE DebtRecords ADD description nvarchar(max) NULL;
ALTER TABLE orderitems ADD storeID INT NULL;
ALTER TABLE productzones ADD storeID INT NULL;

ALTER TABLE users ADD storeID INT NULL;
ALTER TABLE users ADD CONSTRAINT fk_users_store FOREIGN KEY (storeID) REFERENCES store(storeID);

ALTER TABLE zones ADD storeID INT NULL;
ALTER TABLE zones ADD CONSTRAINT fk_zones_store FOREIGN KEY (storeID) REFERENCES store(storeID);

ALTER TABLE products ADD storeID INT NULL;
ALTER TABLE products ADD CONSTRAINT fk_products_store FOREIGN KEY (storeID) REFERENCES store(storeID);

ALTER TABLE orders ADD storeID INT NULL;
ALTER TABLE orders ADD CONSTRAINT fk_orders_store FOREIGN KEY (storeID) REFERENCES store(storeID);

