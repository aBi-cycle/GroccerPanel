INSERT INTO user
(userID, username, password, role)
VALUES
(1,'managerBob','password123','Manager'),
(2,'managerSteve','wordpass987','Manager'),
(3,'employeeMark','employee567','Employee'),
(4,'employeeJohn','pass000word','Employee'),
(5,'employeeCindy','pa55w0rd!!','Employee');


INSERT INTO sale
(saleID, saleName)
VALUES
(1, 'DamagedProduct'),
(2, 'BuyOneGetOne'),
(3, 'PastSeason'),
(4, 'EmployeeDiscount');


INSERT INTO product
(productID, name, description, price, quantity, ImagePath, saleID)
VALUES
(1, 'Dozen Eggs', '1 Dozen Large Brown Eggs', 2.66, 12, '', 1),
(2, '18ct Eggs', '18 count Large White Eggs', 4.79, 18, '', 1),
(3, 'Loaf of Bread', 'Loaf of White Bread', 2.48, 1, '', 1),
(4, 'Ground Beef', '80% Lean Ground Beef Chuck', 6.99, 1, '', 1),
(5, 'Water Bottle Pack', '40 count Bottled Natural Spring Water', 5.18, 40, '', 4),
(6, 'Halloween Candy', 'Assorted Bag of Halloween Candy, Milky Way, Twix, Snickers, KitKat', 13.99, 60, '', 3),
(7, 'Energy Drink', 'Can of Energy Drink', 2.45, 1, '', 3);


INSERT INTO pricereduction
(reductionID, reductionPercent, active)
VALUES
(1, 1.00, 1),
(2, 0.75, 0),
(3, 0.50, 0),
(4, 0.25, 1);


INSERT INTO "order"
(orderID, orderDate, customerName, totalAmount, status)
VALUES
(1, '2026-04-26', 'John Walker', 19.10, 'In Progress'),
(2, '2026-04-26', 'Philip Johnson', 72.35, 'In Progress'),
(3, '2026-04-27', 'Mark Stevens', 52.65, 'In Progress'),
(4, '2026-04-28', 'Allen Jacobs', 124.67, 'In Progress');


INSERT INTO inventory
(inventoryID, totalProducts)
VALUES
(1, 108),
(2, 216),
(3, 12),
(4, 12),
(5, 400),
(6, 5),
(7, 10);


INSERT INTO discount
(discountCode, expirationDate)
VALUES
('001', '2026-04-25'),
('002', '2026-04-27'),
('003', '2026-04-28'),
('004', '9999-12-31');