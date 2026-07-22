-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema finalProjectDB
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema finalProjectDB
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `finalProjectDB` DEFAULT CHARACTER SET utf8 ;
USE `finalProjectDB` ;

-- -----------------------------------------------------
-- Table `finalProjectDB`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `finalProjectDB`.`user` (
  `userID` INT NOT NULL,
  `username` VARCHAR(45) NULL,
  `password` VARCHAR(45) NULL,
  `role` VARCHAR(45) NULL,
  PRIMARY KEY (`userID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `finalProjectDB`.`sale`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `finalProjectDB`.`sale` (
  `saleID` INT NOT NULL,
  `saleName` VARCHAR(45) NULL,
  PRIMARY KEY (`saleID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `finalProjectDB`.`product`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `finalProjectDB`.`product` (
  `productID` INT NOT NULL,
  `name` VARCHAR(45) NULL,
  `description` VARCHAR(100) NULL,
  `price` DOUBLE NULL,
  `quantity` INT NULL,
  `ImagePath` VARCHAR(45) NULL,
  `saleID` INT NULL,
  PRIMARY KEY (`productID`),
  INDEX `saleID_idx` (`saleID` ASC) VISIBLE,
  CONSTRAINT `saleID`
    FOREIGN KEY (`saleID`)
    REFERENCES `finalProjectDB`.`sale` (`saleID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `finalProjectDB`.`inventory`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `finalProjectDB`.`inventory` (
  `inventoryID` INT NOT NULL,
  `totalProducts` INT NULL,
  PRIMARY KEY (`inventoryID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `finalProjectDB`.`pricereduction`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `finalProjectDB`.`pricereduction` (
  `reductionID` INT NOT NULL,
  `reductionPercent` DOUBLE NULL,
  `active` TINYINT NULL,
  PRIMARY KEY (`reductionID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `finalProjectDB`.`order`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `finalProjectDB`.`order` (
  `orderID` INT NOT NULL,
  `orderDate` DATE NULL,
  `customerName` VARCHAR(45) NULL,
  `totalAmount` DOUBLE NULL,
  `status` VARCHAR(45) NULL,
  PRIMARY KEY (`orderID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `finalProjectDB`.`discount`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `finalProjectDB`.`discount` (
  `discountCode` VARCHAR(45) NOT NULL,
  `expirationDate` DATE NULL,
  PRIMARY KEY (`discountCode`))
ENGINE = InnoDB;

CREATE USER 'user' IDENTIFIED BY 'user';

GRANT ALL ON `finalProjectDB`.* TO 'user';

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
