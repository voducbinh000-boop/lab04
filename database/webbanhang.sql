-- Script TUY CHON: ung dung da tu tao database + bang (ddl-auto=update).
-- Chi chay file nay trong HeidiSQL (Laragon) neu muon tao san database kem du lieu mau.
CREATE DATABASE IF NOT EXISTS webbanhang CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE webbanhang;

CREATE TABLE IF NOT EXISTS categories (
  id BIGINT NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS products (
  id BIGINT NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  price DOUBLE NOT NULL,
  description VARCHAR(1000) DEFAULT NULL,
  category_id BIGINT DEFAULT NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_products_category FOREIGN KEY (category_id) REFERENCES categories (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS orders (
  id BIGINT NOT NULL AUTO_INCREMENT,
  customer_name VARCHAR(255) DEFAULT NULL,
  phone VARCHAR(255) DEFAULT NULL,
  address VARCHAR(255) DEFAULT NULL,
  order_date DATETIME(6) DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS order_details (
  id BIGINT NOT NULL AUTO_INCREMENT,
  quantity INT NOT NULL,
  order_id BIGINT DEFAULT NULL,
  product_id BIGINT DEFAULT NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_od_order FOREIGN KEY (order_id) REFERENCES orders (id),
  CONSTRAINT fk_od_product FOREIGN KEY (product_id) REFERENCES products (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Du lieu mau
INSERT INTO categories (name) VALUES ('Điện thoại'), ('Laptop');
INSERT INTO products (name, price, description, category_id) VALUES
 ('Iphone 14', 20000000, 'Điện thoại cũ', 1),
 ('Laptop Dell V1400', 30000000, 'Laptop mới ra năm 2026', 2);
