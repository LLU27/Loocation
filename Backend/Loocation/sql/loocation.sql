-- Drop the database if it exists and create a new one
DROP DATABASE IF EXISTS loocation;
CREATE DATABASE loocation;
USE loocation;

-- Create the user table
CREATE TABLE user (
  user_id INT PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(30) NOT NULL,
  email VARCHAR(50) NOT NULL UNIQUE,
  password VARCHAR(50) NOT NULL
);

-- Create the bathroom table
CREATE TABLE bathroom (
  bathroom_id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL,
  accessibility BOOLEAN NOT NULL,
  changing_station BOOLEAN NOT NULL,
  unisex BOOLEAN NOT NULL
);

-- Create the user_bathroom join table
CREATE TABLE user_bathroom (
  user_bathroom_id INT PRIMARY KEY AUTO_INCREMENT,
  user_id INT,
  bathroom_id INT,
  FOREIGN KEY (user_id) REFERENCES user(user_id),
  FOREIGN KEY (bathroom_id) REFERENCES bathroom(bathroom_id),
  UNIQUE (user_id, bathroom_id)  -- Ensures that a user can only be linked to a bathroom once
);

-- Create the address table
CREATE TABLE address (
  address_id INT PRIMARY KEY AUTO_INCREMENT,
  street VARCHAR(255) NOT NULL,
  city VARCHAR(100) NOT NULL,
  state VARCHAR(100) NOT NULL,
  zipcode VARCHAR(20) NOT NULL,
  latitude DECIMAL(9, 6),
  longitude DECIMAL(9, 6),
  bathroom_id INT,
  FOREIGN KEY (bathroom_id) REFERENCES bathroom(bathroom_id)
);

-- Create the rating table
CREATE TABLE rating (
  id INT PRIMARY KEY AUTO_INCREMENT,
  bathroom_id INT,
  user_id INT,
  rating_value INT NOT NULL CHECK (rating_value BETWEEN 1 AND 5), -- Checking for a 1-5 rating scale
  comment TEXT,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (bathroom_id) REFERENCES bathroom(bathroom_id),
  FOREIGN KEY (user_id) REFERENCES user(user_id)
);
