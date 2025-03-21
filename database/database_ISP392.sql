
USE ISPG4NV1;




CREATE TABLE store ( 
storeID INT PRIMARY KEY IDENTITY(1,1), 
ownerID INT NOT NULL,
storeName VARCHAR(255) NOT NULL, 
address NVARCHAR(255) NOT NULL, 
phone VARCHAR(20) NOT NULL, 
email VARCHAR(255) UNIQUE NOT NULL,
logostore nvarchar(max) NULL,
createAt DATETIME not null DEFAULT GETDATE(), 
updateAt DATETIME NULL, 
createBy INT not NULL, 
isDelete TINYINT DEFAULT 0, 
deleteAt DATETIME NULL, 
deleteBy INT NULL 
);




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
    email nVARCHAR(30) NOT NULL ,
    phone nVARCHAR(10),
	[address] nvarchar(MAX),
	totalDebt DECIMAL(18,2) default 0,
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

CREATE TABLE ProductUnits (
    unitID INT PRIMARY KEY IDENTITY(1,1),
    productID INT NOT NULL,
    unitSize INT NOT NULL CHECK (unitSize > 0),  -- Lưu đơn vị bao dưới dạng số kg
    CONSTRAINT fk_product_unit FOREIGN KEY (productID) REFERENCES products(productID) ON DELETE CASCADE
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
	img nvarchar(MAX),
	status int,
    FOREIGN KEY (customerID) REFERENCES customers(customerID)
);

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



-- Thêm dữ liệu vào bảng store
INSERT INTO store (ownerID, storeName, address, phone, email, createBy)
VALUES
(1, 'Store A', '123 Main St', '0123456789', 'storeA@example.com', 1),
(2, 'Store B', '456 Side St', '0987654321', 'storeB@example.com', 2);

-- Thêm dữ liệu vào bảng users với các role: 1 (admin), 2 (owner), 3 (staff)
INSERT INTO users (userName, userPassword, email, roleID, [image], createBy, storeID)
VALUES
('admin1', 'password123', 'admin1@example.com', 1, 'admin1.jpg', 1, NULL),
('owner1', 'password123', 'owner1@example.com', 2, 'owner1.jpg', 1, 1),
('owner2', 'password123', 'owner2@example.com', 2, 'owner2.jpg', 1, 2),
('staff1', 'password123', 'staff1@example.com', 3, 'staff1.jpg', 2, 1),
('staff2', 'password123', 'staff2@example.com', 3, 'staff2.jpg', 2, 2);

-- Thêm dữ liệu vào bảng customers
INSERT INTO customers ([name], email, phone, [address], createBy, storeID)
VALUES
('Customer A', 'customerA@example.com', '0912345678', '789 Market St', 2, 1),
('Customer B', 'customerB@example.com', '0923456789', '159 Shop St', 3, 2);

-- Thêm dữ liệu vào bảng products
INSERT INTO products (productName, [description], price, quantity, [image], createBy, storeID)
VALUES
('Rice A', 'High-quality rice', 100.00, 50, 'riceA.jpg', 2, 1),
('Rice B', 'Organic rice', 120.00, 30, 'riceB.jpg', 3, 2);

-- Thêm dữ liệu vào bảng orders
INSERT INTO orders (customerID, userID, totalAmount, createBy, storeID, [status])
VALUES
(1, 4, 500.00, 2, 1, 'Pending'),
(2, 5, 360.00, 3, 2, 'Completed');

-- Thêm dữ liệu vào bảng orderitems
INSERT INTO OrderItems (orderID, productID, productName, price, unitPrice, quantity, [description], storeID)
VALUES
(1, 1, 'Rice A', 100.00, 100.00, 5, 'Order 1 - Rice A', 1),
(2, 2, 'Rice B', 120.00, 120.00, 3, 'Order 2 - Rice B', 2);
