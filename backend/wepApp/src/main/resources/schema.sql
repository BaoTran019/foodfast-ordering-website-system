-- Bảng users
CREATE TABLE Users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255),
    address VARCHAR(255),
    phone VARCHAR(20),
    role VARCHAR(50) NOT NULL DEFAULT 'USER'
);

-- Bảng products
CREATE TABLE Products (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    description VARCHAR(255),
    price DOUBLE,
    category VARCHAR(100),
    imageUrl VARCHAR(255)
);

-- Bảng orders
CREATE TABLE Orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    userId INT,
    orderDate TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(50) DEFAULT 'PENDING',
    totalPrice DOUBLE,
    payment VARCHAR(50),
    paymentMethod VARCHAR(50),
    shipping_address VARCHAR(255),
    FOREIGN KEY (userId) REFERENCES Users(id)
);

-- Bảng order_items
CREATE TABLE OrderItems (
    id INT AUTO_INCREMENT PRIMARY KEY,
    orderId INT,
    productId INT,
    quantity INT,
    price DOUBLE,
    FOREIGN KEY (orderId) REFERENCES Orders(id),
    FOREIGN KEY (productId) REFERENCES Products(id)
);

-- Bảng carts
CREATE TABLE Carts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    userId INT,
    totalPrice DOUBLE DEFAULT 0,
    FOREIGN KEY (userId) REFERENCES Users(id)
);

-- Bảng cart_items
CREATE TABLE CartItems (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cartId INT,
    productId INT,
    quantity INT,
    price DOUBLE,
    FOREIGN KEY (cartId) REFERENCES Carts(id),
    FOREIGN KEY (productId) REFERENCES Products(id)
);

-- Bảng payments
CREATE TABLE Payments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    orderId INT,
    amount DOUBLE,
    paymentDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    paymentMethod VARCHAR(50),
    status VARCHAR(50),
    FOREIGN KEY (orderId) REFERENCES Orders(id)
);
