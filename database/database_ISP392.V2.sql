-- Tạo Database
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
    createAt DATETIME NOT NULL DEFAULT GETDATE(), 
    updateAt DATETIME NULL, 
    createBy INT NOT NULL, 
    isDelete TINYINT DEFAULT 0, 
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
    [image] NVARCHAR(MAX) NULL,  -- Sửa lại cho phép NULL
    storeID INT NULL,
    createAt DATETIME NOT NULL DEFAULT GETDATE(),
    updateAt DATETIME,
    createBy INT NOT NULL,
    isDelete BIT DEFAULT 0,
    deleteAt DATETIME,
    deleteBy INT,
    FOREIGN KEY (storeID) REFERENCES store(storeID) ON DELETE CASCADE
);

-- Bảng Customers
CREATE TABLE customers (
    customerID INT PRIMARY KEY IDENTITY(1,1),
    [name] NVARCHAR(30) NOT NULL,
    email NVARCHAR(30) NOT NULL ,
    phone NVARCHAR(15),
    [address] NVARCHAR(100),
    totalDebt DECIMAL(18,2) DEFAULT 0,
    storeID INT NULL,
    createAt DATETIME NOT NULL DEFAULT GETDATE(),
    updateAt DATETIME,
    createBy INT NOT NULL,
    isDelete BIT DEFAULT 0,
    deleteAt DATETIME,
    deleteBy INT,
    FOREIGN KEY (storeID) REFERENCES store(storeID) ON DELETE CASCADE
);

-- Bảng Orders
CREATE TABLE orders (
    orderID INT PRIMARY KEY IDENTITY(1,1),
    customerID INT NOT NULL,
    userID INT NOT NULL,
    totalAmount DECIMAL(18,2) NOT NULL CHECK (totalAmount >= 0),
    storeID INT NULL,
    createAt DATETIME NOT NULL DEFAULT GETDATE(),
    updateAt DATETIME,
    createBy INT NOT NULL,
    isDelete BIT DEFAULT 0,
    deleteAt DATETIME,
    deleteBy INT,
    porter INT,
    [status] NVARCHAR(255),
    FOREIGN KEY (customerID) REFERENCES customers(customerID) ON DELETE CASCADE,
    FOREIGN KEY (userID) REFERENCES users(ID) ON DELETE CASCADE,
    FOREIGN KEY (storeID) REFERENCES store(storeID) ON DELETE CASCADE
);

-- Bảng Zones
CREATE TABLE zones (
    zoneID INT PRIMARY KEY IDENTITY(1,1),
    zoneName NVARCHAR(100) NOT NULL,
    storeID INT NULL,
    createAt DATETIME NOT NULL DEFAULT GETDATE(),
    updateAt DATETIME,
    createBy INT NOT NULL,
    isDelete BIT DEFAULT 0,
    deleteAt DATETIME,
    deleteBy INT,
    FOREIGN KEY (storeID) REFERENCES store(storeID) ON DELETE CASCADE
);

-- Bảng Products
CREATE TABLE products (
    productID INT PRIMARY KEY IDENTITY(1,1),
    productName NVARCHAR(100) NOT NULL,
    [description] NVARCHAR(MAX),
    price DECIMAL(18,2) NOT NULL CHECK (price >= 0),
    quantity INT CHECK (quantity >= 0) NOT NULL DEFAULT 0,
    [image] NVARCHAR(MAX),
    storeID INT NULL,
    createAt DATETIME NOT NULL DEFAULT GETDATE(),
    updateAt DATETIME,
    createBy INT NOT NULL,
    isDelete BIT DEFAULT 0,
    deleteAt DATETIME,
    deleteBy INT,
    FOREIGN KEY (storeID) REFERENCES store(storeID) ON DELETE CASCADE
);

-- Bảng ProductUnits
CREATE TABLE ProductUnits (
    unitID INT PRIMARY KEY IDENTITY(1,1),
    productID INT NOT NULL,
    unitSize INT NOT NULL CHECK (unitSize > 0),
    storeID INT NULL,
    FOREIGN KEY (productID) REFERENCES products(productID) ON DELETE CASCADE,
    FOREIGN KEY (storeID) REFERENCES store(storeID) ON DELETE CASCADE
);

-- Bảng ProductZones
CREATE TABLE ProductZones (
    productID INT NOT NULL,
    zoneID INT NOT NULL,
    storeID INT NULL,
    PRIMARY KEY (productID, zoneID),
    createAt DATETIME NOT NULL DEFAULT GETDATE(),
    updateAt DATETIME,
    createBy INT NOT NULL,
    isDelete BIT DEFAULT 0,
    deleteAt DATETIME,
    deleteBy INT,
    FOREIGN KEY (productID) REFERENCES products(productID) ON DELETE CASCADE,
    FOREIGN KEY (zoneID) REFERENCES zones(zoneID) ON DELETE CASCADE,
    FOREIGN KEY (storeID) REFERENCES store(storeID) ON DELETE CASCADE
);

-- Bảng OrderItems
CREATE TABLE OrderItems (
    orderitemID INT PRIMARY KEY IDENTITY(1,1),
    orderID INT NOT NULL,
    productID INT NOT NULL,
    productName NVARCHAR(100) NOT NULL,
    price DECIMAL(18,2) NOT NULL CHECK (price >= 0),
    unitPrice DECIMAL(18,2) NOT NULL CHECK (unitPrice >= 0),
    quantity INT CHECK (quantity >= 0) NOT NULL,
    [description] NVARCHAR(255),
    storeID INT NULL,
    FOREIGN KEY (productID) REFERENCES products(productID) ON DELETE CASCADE,
    FOREIGN KEY (orderID) REFERENCES orders(orderID) ON DELETE CASCADE,
    FOREIGN KEY (storeID) REFERENCES store(storeID) ON DELETE CASCADE
);

-- Bảng DebtRecords
CREATE TABLE DebtRecords (
    debtID INT PRIMARY KEY IDENTITY(1,1),
    customerID INT NOT NULL,
    orderID INT NOT NULL,
    amount DECIMAL(18,2) NOT NULL CHECK (amount >= 0),
    paymentStatus BIT NOT NULL DEFAULT 0,
    description NVARCHAR(MAX) NULL,
    storeID INT NULL,
    createAt DATETIME NOT NULL DEFAULT GETDATE(),
    updateAt DATETIME,
    createBy INT NOT NULL,
    isDelete BIT DEFAULT 0,
    deleteAt DATETIME,
    deleteBy INT,
    FOREIGN KEY (customerID) REFERENCES customers(customerID) ON DELETE CASCADE,
    FOREIGN KEY (storeID) REFERENCES store(storeID) ON DELETE CASCADE
);



-- Thêm dữ liệu mẫu cho bảng store
INSERT INTO store (ownerID, storeName, address, phone, email, logostore, createBy)
VALUES
(1, 'Gạo Sạch ABC', '123 Đường Lê Lợi, TP.HCM', '0901234567', 'abc_store@gmail.com', NULL, 1),
(2, 'Gạo Việt', '456 Đường Trần Hưng Đạo, Hà Nội', '0912345678', 'viet_store@gmail.com', NULL, 2);

-- Thêm dữ liệu mẫu cho bảng users
INSERT INTO users (userName, userPassword, email, roleID, storeID, createBy)
VALUES
('admin', 'hashed_password1', 'admin@gmail.com', 1, 1, 1),
('employee1', 'hashed_password2', 'employee1@gmail.com', 2, 1, 1),
('employee2', 'hashed_password3', 'employee2@gmail.com', 2, 2, 2);

-- Thêm dữ liệu mẫu cho bảng customers
INSERT INTO customers (name, email, phone, address, totalDebt, storeID, createBy)
VALUES
('Nguyễn Văn A', 'nva@gmail.com', '0987654321', 'Quận 1, TP.HCM', 50000, 1, 1),
('Trần Thị B', 'ttb@gmail.com', '0976543210', 'Hoàn Kiếm, Hà Nội', 20000, 2, 2);

-- Thêm dữ liệu mẫu cho bảng orders
INSERT INTO orders (customerID, userID, totalAmount, storeID, createBy, porter, status)
VALUES
(1, 2, 300000, 1, 1, 1, 'Đang xử lý'),
(2, 3, 450000, 2, 2, 2, 'Hoàn thành');

-- Thêm dữ liệu mẫu cho bảng zones
INSERT INTO zones (zoneName, storeID, createBy)
VALUES
('Khu A', 1, 1),
('Khu B', 2, 2);

-- Thêm dữ liệu mẫu cho bảng products
INSERT INTO products (productName, description, price, quantity, storeID, createBy)
VALUES
('Gạo Lứt', 'Gạo lứt hữu cơ, tốt cho sức khỏe', 25000, 100, 1, 1),
('Gạo ST25', 'Gạo ngon nhất thế giới năm 2019', 30000, 200, 2, 2);

-- Thêm dữ liệu mẫu cho bảng ProductUnits
INSERT INTO ProductUnits (productID, unitSize, storeID)
VALUES
(1, 1, 1),
(2, 5, 2);

-- Thêm dữ liệu mẫu cho bảng ProductZones
INSERT INTO ProductZones (productID, zoneID, storeID, createBy)
VALUES
(1, 1, 1, 1),
(2, 2, 2, 2);

-- Thêm dữ liệu mẫu cho bảng OrderItems
INSERT INTO OrderItems (orderID, productID, productName, price, unitPrice, quantity, description, storeID)
VALUES
(1, 1, 'Gạo Lứt', 25000, 25000, 10, 'Đơn hàng gạo lứt', 1),
(2, 2, 'Gạo ST25', 30000, 30000, 15, 'Đơn hàng gạo ST25', 2);

-- Thêm dữ liệu mẫu cho bảng DebtRecords
INSERT INTO DebtRecords (customerID, orderID, amount, paymentStatus, description, storeID, createBy)
VALUES
(1, 1, 50000, 0, 'Nợ đơn hàng 1', 1, 1),
(2, 2, 20000, 1, 'Đã thanh toán đơn hàng 2', 2, 2);
