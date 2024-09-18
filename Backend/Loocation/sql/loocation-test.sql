DROP DATABASE IF EXISTS loocation_test;
CREATE DATABASE loocation_test;
USE loocation_test;

CREATE TABLE user (
  user_id INT PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(30) NOT NULL,
  email VARCHAR(50) NOT NULL UNIQUE,
  password VARCHAR(50) NOT NULL
);

CREATE TABLE address (
  address_id INT PRIMARY KEY AUTO_INCREMENT,
  street VARCHAR(255) NOT NULL,
  city VARCHAR(100) NOT NULL,
  state VARCHAR(100) NOT NULL,
  zipcode VARCHAR(20) NOT NULL,
  latitude DECIMAL(9, 6),
  longitude DECIMAL(9, 6)
);

CREATE TABLE bathroom (
  bathroom_id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL,
  accessibility BOOLEAN NOT NULL,
  changing_station BOOLEAN NOT NULL,
  unisex BOOLEAN NOT NULL,
  address_id INT,
  FOREIGN KEY (address_id) REFERENCES address(address_id)
);

CREATE TABLE user_bathroom (
  user_id INT,
  bathroom_id INT,
  FOREIGN KEY (user_id) REFERENCES user(user_id),
  FOREIGN KEY (bathroom_id) REFERENCES bathroom(bathroom_id),
  PRIMARY KEY (user_id, bathroom_id)
);

CREATE TABLE rating (
  rating_id INT PRIMARY KEY AUTO_INCREMENT,
  bathroom_id INT,
  user_id INT,
  rating INT NOT NULL CHECK (rating BETWEEN 1 AND 5),
  comment TEXT,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (bathroom_id) REFERENCES bathroom(bathroom_id),
  FOREIGN KEY (user_id) REFERENCES user(user_id)
);

delimiter //
CREATE PROCEDURE set_known_good_state()
BEGIN
  DELETE FROM user_bathroom;
  DELETE FROM rating;
  DELETE FROM bathroom;
  DELETE FROM address;
  DELETE FROM `user`;

  ALTER TABLE `user` AUTO_INCREMENT = 1;
  ALTER TABLE address AUTO_INCREMENT = 1;
  ALTER TABLE bathroom AUTO_INCREMENT = 1;
  ALTER TABLE rating AUTO_INCREMENT = 1;

  -- Insert sample data into `user`
  INSERT INTO `user` (username, email, password) VALUES
  ('john_doe', 'john.doe@example.com', 'password123'),
  ('jane_smith', 'jane.smith@example.com', 'password456'),
  ('alice_jones', 'alice.jones@example.com', 'password789');

  -- Insert sample data into `address`
  INSERT INTO address (street, city, state, zipcode, latitude, longitude) VALUES
  ('123 Main St', 'Buffalo', 'NY', '14201', 42.8877, -78.8784),
  ('456 Elm St', 'Buffalo', 'NY', '14202', 42.8957, -78.8784),
  ('789 Oak St', 'Buffalo', 'NY', '14203', 42.9047, -78.8784);

  -- Insert sample data into `bathroom`
  INSERT INTO bathroom (name, accessibility, changing_station, unisex, address_id) VALUES
  ('Bathroom A', TRUE, TRUE, FALSE, 1),
  ('Bathroom B', TRUE, FALSE, TRUE, 2),
  ('Bathroom C', FALSE, TRUE, TRUE, 3);

  -- Insert sample data into `user_bathroom`
  INSERT INTO user_bathroom (user_id, bathroom_id) VALUES
  (1, 1),
  (1, 2),
  (2, 1),
  (3, 3);

  -- Insert sample data into `rating`
  INSERT INTO rating (bathroom_id, user_id, rating, comment) VALUES
  (1, 1, 5, 'Great bathroom! Clean and accessible.'),
  (1, 2, 4, 'Good bathroom, but could use more privacy.'),
  (2, 1, 3, 'Decent bathroom, but no changing station.'),
  (3, 3, 5, 'Excellent facilities.'),
  (3, 2, 2, 'Not very clean.');
END;
//
delimiter ;
CALL set_known_good_state();