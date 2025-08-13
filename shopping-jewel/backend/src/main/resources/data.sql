-- Basic seed: users, products
INSERT INTO users (id, username, password, email) VALUES (1, 'admin', '$2a$10$wq9b8/0YzQhGv/OTqQp2ku4o9D0zqZs7YgYq6bIuQ6pJ9rV0JZ5yK', 'admin@example.com'); -- password: admin123
INSERT INTO users (id, username, password, email) VALUES (2, 'user', '$2a$10$7qZ8HcL9a8nX2u3Z9sV4weQ0Y7AaF8sLkC9T1zQ2bO7yF8uG9H1e', 'user@example.com'); -- password: user123

INSERT INTO product (id, name, description, price, tags, image_url, rating_count, rating_avg) VALUES
(1, 'Sample T-Shirt', 'Comfortable cotton tee', 19.99, 'NEW_ARRIVAL,POPULAR', 'https://via.placeholder.com/200', 0, 0.0),
(2, 'Running Shoes', 'Lightweight running shoes', 59.99, 'BEST_SELLER,OFFER', 'https://via.placeholder.com/200', 0, 0.0),
(3, 'Backpack', 'Durable travel backpack', 39.50, 'POPULAR', 'https://via.placeholder.com/200', 0, 0.0);
