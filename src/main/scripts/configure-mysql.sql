## Use to run mysql db docker image, optional if you're not using a local mysqldb
# docker run --name mariadb -e MYSQL_ALLOW_EMPTY_PASSWORD=yes -p 3306:3306 -d mariadb

# Connect to mysql and run as root user
# Create Databases
CREATE DATABASE recipe_dev;
CREATE DATABASE recipe_prod;

# Create database service accounts
CREATE USER 'recipe_dev'@'localhost' IDENTIFIED BY 'zse44rfv';
CREATE USER 'recipe_prod'@'localhost' IDENTIFIED BY 'zse44rfv';
CREATE USER 'recipe_dev'@'%' IDENTIFIED BY 'zse44rfv';
CREATE USER 'recipe_prod'@'%' IDENTIFIED BY 'zse44rfv';

# Database grants
GRANT SELECT ON recipe_dev.* to 'recipe_dev'@'localhost';
GRANT INSERT ON recipe_dev.* to 'recipe_dev'@'localhost';
GRANT DELETE ON recipe_dev.* to 'recipe_dev'@'localhost';
GRANT UPDATE ON recipe_dev.* to 'recipe_dev'@'localhost';
GRANT SELECT ON recipe_prod.* to 'recipe_prod'@'localhost';
GRANT INSERT ON recipe_prod.* to 'recipe_prod'@'localhost';
GRANT DELETE ON recipe_prod.* to 'recipe_prod'@'localhost';
GRANT UPDATE ON recipe_prod.* to 'recipe_prod'@'localhost';
GRANT SELECT ON recipe_dev.* to 'recipe_dev'@'%';
GRANT INSERT ON recipe_dev.* to 'recipe_dev'@'%';
GRANT DELETE ON recipe_dev.* to 'recipe_dev'@'%';
GRANT UPDATE ON recipe_dev.* to 'recipe_dev'@'%';
GRANT SELECT ON recipe_prod.* to 'recipe_prod'@'%';
GRANT INSERT ON recipe_prod.* to 'recipe_prod'@'%';
GRANT DELETE ON recipe_prod.* to 'recipe_prod'@'%';
GRANT UPDATE ON recipe_prod.* to 'recipe_prod'@'%';
