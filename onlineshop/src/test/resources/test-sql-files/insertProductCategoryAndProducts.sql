INSERT INTO product_category(product_category_id, category_name) VALUES (15, 'Books');

INSERT INTO product_category(product_category_id, category_name) VALUES (16, 'Mugs');

INSERT INTO product (product_id,upc, name, description, image_url, active, units_in_stock, unit_price, product_category_id,date_created) VALUES
                (1001, 'BOOK-TECH-1001', 'Crash Course in Python', 'Learn Python at your own pace. The author explains how the technology works in easy-to-understand language. This book includes working examples that you can apply to your own projects. Purchase the book and get started today!', 'assets/images/products/books/book-1000.png', 1, 100, 14.99, 15, NOW());

INSERT INTO product (product_id,upc, name, description, image_url, active, units_in_stock, unit_price, product_category_id,date_created) VALUES
                (1002, 'BOOK-TECH-1002', 'Book2', 'Test 2 book', 'assets/images/products/books/book-1000.png', 1, 100, 14.99, 15, NOW());

INSERT INTO product (product_id,upc, name, description, image_url, active, units_in_stock, unit_price, product_category_id,date_created) VALUES
                (1003, 'MUG-1003', 'Mug 1', 'Blue mug!', 'assets/images/products/books/mug-1000.png', 1, 100, 14.99, 16, NOW());
