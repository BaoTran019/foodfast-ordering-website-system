-- =========================
-- USERS (20 users)
-- =========================
INSERT INTO Users (name, password, email, address, phone, role) VALUES 
('user1', 'pass1', 'user1@example.com', 'Address 1', '0123456781', 'ADMIN'),
('user2', 'pass2', 'user2@example.com', 'Address 2', '0123456782', 'USER'),
('user3', 'pass3', 'user3@example.com', 'Address 3', '0123456783', 'USER'),
('user4', 'pass4', 'user4@example.com', 'Address 4', '0123456784', 'USER'),
('user5', 'pass5', 'user5@example.com', 'Address 5', '0123456785', 'ADMIN'),
('user6', 'pass6', 'user6@example.com', 'Address 6', '0123456786', 'USER'),
('user7', 'pass7', 'user7@example.com', 'Address 7', '0123456787', 'USER'),
('user8', 'pass8', 'user8@example.com', 'Address 8', '0123456788', 'USER'),
('user9', 'pass9', 'user9@example.com', 'Address 9', '0123456789', 'USER'),
('user10', 'pass10', 'user10@example.com', 'Address 10', '0123456790', 'USER'),
('user11', 'pass11', 'user11@example.com', 'Address 11', '0123456791', 'USER'),
('user12', 'pass12', 'user12@example.com', 'Address 12', '0123456792', 'USER'),
('user13', 'pass13', 'user13@example.com', 'Address 13', '0123456793', 'USER'),
('user14', 'pass14', 'user14@example.com', 'Address 14', '0123456794', 'USER'),
('user15', 'pass15', 'user15@example.com', 'Address 15', '0123456795', 'USER'),
('user16', 'pass16', 'user16@example.com', 'Address 16', '0123456796', 'USER'),
('user17', 'pass17', 'user17@example.com', 'Address 17', '0123456797', 'USER'),
('user18', 'pass18', 'user18@example.com', 'Address 18', '0123456798', 'USER'),
('user19', 'pass19', 'user19@example.com', 'Address 19', '0123456799', 'USER'),
('user20', 'pass20', 'user20@example.com', 'Address 20', '0123456700', 'USER');

-- =========================
-- PRODUCTS (20 products)
-- =========================
INSERT INTO Products (name, description, price, category, imageUrl) VALUES
('Combo Gà giòn - 1 miếng', 'Phần ăn gồm: 01 Gà giòn + 01 Khoai tây chiên (S) + 01 Nước ngọt', 55000.0, 'Fried_Chicken', 'menu/combo_1_mieng_ga_gion.jpg'),
('Combo Gà giòn - 2 miếng', 'Phần ăn gồm: 02 Gà giòn + 01 Khoai tây chiên (S) + 01 Nước ngọt', 55000.0, 'Fried_Chicken', 'menu/1_mieng_ga_gion.jpg'),
('1 miếng gà giòn', 'Gà giòn - Phần gà được phục vụ ngẫu nhiên', 35000.0, 'Fried_Chicken', 'menu/1_mieng_ga_gion.jpg'),
('3 miếng gà giòn', '3 miếng gà giòn', 100000.0, 'Fried_Chicken', 'menu/3_mieng_ga_gion.jpg'),
('6 miếng gà giòn', '6 miếng gà giòn', 200000.0, 'Fried_Chicken', 'menu/3_mieng_ga_gion.jpg'),
('9 miếng gà giòn', '9 miếng gà giòn', 300000.0, 'Fried_Chicken', 'menu/3_mieng_ga_gion.jpg'),

('Headphones 1', 'Noise cancelling headphones', 200.0, 'Accessories', 'headphones1.jpg'),
('Headphones 2', 'Wireless headphones', 150.0, 'Accessories', 'headphones2.jpg'),
('Keyboard 1', 'Mechanical keyboard', 100.0, 'Accessories', 'keyboard1.jpg'),
('Keyboard 2', 'Wireless keyboard', 80.0, 'Accessories', 'keyboard2.jpg'),
('Mouse 1', 'Gaming mouse', 70.0, 'Accessories', 'mouse1.jpg'),
('Mouse 2', 'Wireless mouse', 50.0, 'Accessories', 'mouse2.jpg'),
('Monitor 1', '4K monitor', 400.0, 'Electronics', 'monitor1.jpg'),
('Monitor 2', 'Full HD monitor', 200.0, 'Electronics', 'monitor2.jpg'),
('Smartwatch 1', 'Fitness smartwatch', 250.0, 'Wearables', 'watch1.jpg'),
('Smartwatch 2', 'Luxury smartwatch', 500.0, 'Wearables', 'watch2.jpg'),
('Camera 1', 'DSLR Camera', 1000.0, 'Electronics', 'camera1.jpg'),
('Camera 2', 'Compact camera', 600.0, 'Electronics', 'camera2.jpg'),
('Printer 1', 'Laser printer', 300.0, 'Office', 'printer1.jpg'),
('Printer 2', 'Inkjet printer', 150.0, 'Office', 'printer2.jpg');

-- =========================
-- ORDERS (20 orders, each belongs to userId 1..20)
-- =========================
INSERT INTO Orders (userId, orderDate, status, totalPrice, payment, paymentMethod, shipping_address) VALUES
(1,  CURRENT_TIMESTAMP, 'PENDING',   2300.0, 'UNPAID', 'COD',    'Address 1'),
(2,  CURRENT_TIMESTAMP, 'SHIPPED',   1600.0, 'PAID',   'VNPAY',  'Address 2'),
(3,  CURRENT_TIMESTAMP, 'DELIVERED', 1500.0, 'PAID',   'MOMO',   'Address 3'),
(4,  CURRENT_TIMESTAMP, 'PENDING',   1200000.0, 'UNPAID', 'COD',    'Address 4'),
(5,  CURRENT_TIMESTAMP, 'SHIPPED',    400.0, 'PAID',   'VNPAY',  'Address 5'),
(6,  CURRENT_TIMESTAMP, 'DELIVERED', 1700.0, 'PAID',   'MOMO',   'Address 6'),
(7,  CURRENT_TIMESTAMP, 'PENDING',    650.0, 'UNPAID', 'COD',    'Address 7'),
(8,  CURRENT_TIMESTAMP, 'SHIPPED',    900.0, 'PAID',   'VNPAY',  'Address 8'),
(9,  CURRENT_TIMESTAMP, 'DELIVERED',  300.0, 'PAID',   'MOMO',   'Address 9'),
(10, CURRENT_TIMESTAMP, 'CANCELLED',    750.0, 'UNPAID', 'COD',    'Address 10'),
(11, CURRENT_TIMESTAMP, 'SHIPPED',   1100.0, 'PAID',   'VNPAY',  'Address 11'),
(12, CURRENT_TIMESTAMP, 'DELIVERED', 1600.0, 'PAID',   'MOMO',   'Address 12'),
(13, CURRENT_TIMESTAMP, 'CANCELLED',    900.0, 'UNPAID', 'COD',    'Address 13'),
(14, CURRENT_TIMESTAMP, 'SHIPPED',   1980.0, 'PAID',   'VNPAY',  'Address 14'),
(15, CURRENT_TIMESTAMP, 'DELIVERED', 1250.0, 'PAID',   'MOMO',   'Address 15'),
(16, CURRENT_TIMESTAMP, 'PENDING',    330.0, 'UNPAID', 'COD',    'Address 16'),
(17, CURRENT_TIMESTAMP, 'CANCELLED',    960.0, 'PAID',   'VNPAY',  'Address 17'),
(18, CURRENT_TIMESTAMP, 'PENDING',    720.0, 'PAID',   'MOMO',   'Address 18'),
(19, CURRENT_TIMESTAMP, 'SHIPPED',   1300.0, 'UNPAID', 'COD',    'Address 19'),
(20, CURRENT_TIMESTAMP, 'PENDING',   2100.0, 'PAID',   'VNPAY',  'Address 20');


-- =========================
-- ORDER ITEMS (2–3 items per order)
-- =========================
INSERT INTO OrderItems (orderId, productId, quantity, price) VALUES
(1, 1, 1, 1500.0), (1, 3, 1, 800.0),
(2, 3, 2, 800.0),
(3, 1, 1, 1500.0),
(4, 2, 1, 1200.0),
(5, 4, 1, 400.0),
(6, 5, 2, 1200.0), (6, 7, 1, 500.0),
(7, 8, 1, 150.0), (7, 9, 1, 500.0),
(8, 10, 1, 400.0), (8, 11, 1, 500.0),
(9, 12, 1, 300.0),
(10, 13, 1, 400.0), (10, 14, 1, 350.0),
(11, 15, 1, 250.0), (11, 16, 1, 850.0),
(12, 17, 1, 1000.0), (12, 18, 1, 600.0),
(13, 19, 2, 450.0),
(14, 20, 2, 990.0),
(15, 2, 1, 1200.0), (15, 9, 1, 50.0),
(16, 4, 1, 330.0),
(17, 6, 2, 480.0),
(18, 7, 1, 200.0), (18, 8, 1, 520.0),
(19, 13, 1, 400.0), (19, 15, 1, 900.0),
(20, 1, 1, 1500.0), (20, 11, 1, 600.0);

-- =========================
-- CARTS (20 carts, each belongs to userId 1..20)
-- =========================
INSERT INTO Carts (userId, totalPrice) VALUES
(1, 300000), (2, 270000), (3, 350000), (4, 400000), (5, 480000),
(6, 500000), (7, 600000), (8, 700000), (9, 800000), (10, 900000),
(11, 1000000), (12, 1100000), (13, 1200000), (14, 1300000), (15, 1400000),
(16, 1500000), (17, 1600000), (18, 1700000), (19, 1800000), (20, 1900000);

-- =========================
-- CART ITEMS (2–3 per cart)
-- =========================
INSERT INTO CartItems (cartId, productId, quantity, price) VALUES
(1, 1, 1, 100000), (1, 2, 2, 200000),
(2, 3, 1, 150000), (2, 5, 1, 120000),
(3, 4, 2, 180000), (3, 6, 1, 170000),
(4, 7, 1, 210000), (4, 8, 2, 220000),
(5, 9, 1, 250000), (5, 10, 1, 230000),
(6, 11, 2, 300000), (6, 12, 1, 270000),
(7, 13, 1, 310000), (7, 14, 2, 320000),
(8, 15, 1, 350000), (8, 16, 1, 360000),
(9, 17, 1, 370000), (9, 18, 1, 380000),
(10, 19, 2, 400000), (10, 20, 1, 420000);

-- =========================
-- PAYMENTS (10 payments for orders 2,3,5,6,8,9,11,12,14,15)
-- =========================
INSERT INTO Payments (orderId, amount, paymentDate, paymentMethod, status) VALUES 
(2, 1600.0, CURRENT_TIMESTAMP, 'VNPAY', 'SUCCESS'),
(3, 1500.0, CURRENT_TIMESTAMP, 'MOMO', 'FAILED'),
(5, 400.0, CURRENT_TIMESTAMP, 'VNPAY', 'PENDING'),
(6, 1700.0, CURRENT_TIMESTAMP, 'MOMO', 'SUCCESS'),
(8, 900.0, CURRENT_TIMESTAMP, 'VNPAY', 'CANCELLED'),
(9, 300.0, CURRENT_TIMESTAMP, 'MOMO', 'SUCCESS'),
(11, 1100.0, CURRENT_TIMESTAMP, 'VNPAY', 'FAILED'),
(12, 1600.0, CURRENT_TIMESTAMP, 'MOMO', 'SUCCESS'),
(14, 1980.0, CURRENT_TIMESTAMP, 'VNPAY', 'PENDING');
-- (15, 1250.0, CURRENT_TIMESTAMP, 'MOMO', 'SUCCESS');
