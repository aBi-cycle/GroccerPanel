INSERT INTO user
(userID, username, password, role)
VALUES
(1,'managerBob','password123','Manager'),
(2,'managerSteve','wordpass987','Manager'),
(3,'employeeMark','employee567','Employee'),
(4,'employeeJohn','pass000word','Employee'),
(5,'employeeCindy','pa55w0rd!!','Employee'),
(6,'temp', 'test', 'Employee'),
(7,'manager', 'test', 'Manager');



INSERT INTO product
(productID, name, description, price, quantity, ImagePath)
VALUES
(1, 'Dozen Eggs', '1 Dozen Large Brown Eggs', 2.66, 12, ''),
(2, '18ct Eggs', '18 count Large White Eggs', 4.79, 18, ''),
(3, 'Loaf of Bread', 'Loaf of White Bread', 2.48, 1, ''),
(4, 'Ground Beef', '80% Lean Ground Beef Chuck', 6.99, 1, ''),
(5, 'Water Bottle Pack', '40 count Bottled Natural Spring Water', 5.18, 40, ''),
(6, 'Halloween Candy', 'Assorted Bag of Halloween Candy, Milky Way, Twix, Snickers, KitKat', 13.99, 60, ''),
(7, 'Energy Drink', 'Can of Energy Drink', 2.45, 1, '');


INSERT INTO orders
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

INSERT INTO Discount (code, discountType, discountValue, active, expirationDate)
VALUES
('SAVE10', 'Percent', 10, 1, '2026-12-31'),
('SAVE20', 'Percent', 20, 1, '2026-12-31'),
('WELCOME5', 'Fixed', 5.00, 1, '2026-10-01'),
('MILK15', 'Percent', 15, 1, '2026-09-15'),
('BOGO50', 'Percent', 50, 1, '2026-08-31'),
('EXPIRED10', 'Percent', 10, 0, '2025-12-31');

INSERT INTO ProductDiscount
(productID, discountID)
VALUES
(1, 1),
(2, 1),
(3, 2),
(4, 3),
(5, 4),
(6, 5),
(7, 6);

