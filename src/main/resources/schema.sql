CREATE TABLE booking (
  id INT AUTO_INCREMENT  PRIMARY KEY NOT NULL,
  customer_id VARCHAR(250),
  clinic_id VARCHAR(250),
  service_id VARCHAR(250),
  date DATE,
  start_time VARCHAR(50),
  end_time VARCHAR(50)
);