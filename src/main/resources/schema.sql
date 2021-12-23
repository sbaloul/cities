CREATE TABLE cities(id INT PRIMARY KEY, name VARCHAR(255), photo VARCHAR(1000))
  AS SELECT * FROM CSVREAD('classpath:cities.csv');

CREATE INDEX cities_name_idx ON cities(name);