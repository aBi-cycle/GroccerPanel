USE finalprojectdb;

INSERT INTO user
(userID, username, password, role)
VALUES
('001','managerBob','password123','Manager'),
('002','managerSteve','wordpass987','Manager'),
('003','employeeMark','employee567','Employee'),
('004','employeeJohn','pass000word','Employee'),
('005','employeeCindy','pa55w0rd!!','Employee');

INSERT INTO sale
(saleID, saleName)
VALUES
('001', 'DamagedProduct'),
('002', 'BuyOneGetOne'),
('003', 'PastSeason'),
('004', 'EmployeeDiscount');

INSERT INTO product
(productID, name, description, price, quantity, ImagePath, saleID)
VALUES
('001', 'Dozen Eggs', '1 Dozen Larg Brown Eggs', '2.66', '12', '', '001'),
('002', '18ct Eggs', '18 count Large White Eggs', '4.79', '18', '', '001'),
('003', 'Loaf of Bread', 'Loaf of White Bread', '2.48', '1', '', '001'),
('004', 'Ground Beef', '80% Lean Ground Beef Chuck', '6.99', '1', '', '001'),
('005', 'Water Bottle Pack', '40 count Bottled Natural Spring Water', '5.18', '40', '', '004'),
('006', 'Halloween Candy', 'Assorted Bag of Halloween Candy, Milky Way, Twix, Snickers, KitKat', '13.99', '60', '', '003'),
('007', 'Energy Drink', 'Can of Energy Drink', '2.45', '1', '', '003');

INSERT INTO pricereduction
(reductionID, reductionPercent, active)
VALUES
('001', 1.00, '1'),
('002', 0.75, '0'),
('003', 0.50, '0'),
('004', 0.25, '1');
INSERT INTO finalprojectdb.order
(orderID, orderDate, customerName, totalAmount, status)
VALUES
('001', '2026-04-26', 'John Walker', '19.10', 'In Progress'),
('002', '2026-04-26', 'Philip Johnson', '72.35', 'In Progress'),
('003', '2026-04-27', 'Mark Stevens', '52.65', 'In Progress'),
('004', '2026-04-28', 'Allen Jacobs', '124.67', 'In Progress');

INSERT INTO inventory
(inventoryID, totalProducts)
VALUES
('001', '108'),
('002', '216'),
('003', '12'),
('004', '12'),
('005', '400'),
('006', '5'),
('007', '10');

INSERT INTO discount
(discountCode, expirationDate)
VALUES
('001', "2026-04-25"),
('002', "2026-04-27"),
('003', "2026-04-28"),
('004', "9999-12-31");