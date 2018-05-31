-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema musicstore
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema musicstore
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `musicstore` DEFAULT CHARACTER SET utf8 ;
USE `musicstore` ;

-- -----------------------------------------------------
-- Table `musicstore`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `musicstore`.`users` (
  `userId` INT NOT NULL AUTO_INCREMENT COMMENT 'Синтетический ключ, для использования его в связях',
  `nickName` VARCHAR(45) NOT NULL COMMENT 'Обязательное имя пользователя для системы. Альтернативный ключ',
  `email` VARCHAR(45) NOT NULL COMMENT 'Почта пользователя, обязательно используемая при регистрации пользователя. Альтернативный ключ',
  `role` ENUM('user', 'admin') NOT NULL COMMENT 'Роль пользователя (Обычный пользователь/администратор)',
  `cash` INT NOT NULL COMMENT 'Сумма на счёте пользователя  в системе',
  `bonus` SET('free track', 'free album', 'free assemblage') NULL COMMENT 'Система бонусов для пользователя. Бонусы не суммируются (может одноременно присутствовать только 1 бонус каждого вида). Виды: 1 бесплатный трек, 1 бесплатный альбом, 1 бесплатная сборка. Может быть NULL - бонусов нет',
  `discount` TINYINT NOT NULL COMMENT 'Скидка данному пользователю. Хранится в процентах. TINYINT - скидка от 0 до 100%',
  `password` VARCHAR(45) NOT NULL COMMENT 'Хэш-код пароля. Необходим для аутентификации пользователя.',
  PRIMARY KEY (`userId`),
  UNIQUE INDEX `nickName_UNIQUE` (`nickName` ASC),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `musicstore`.`performers`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `musicstore`.`performers` (
  `performerId` INT NOT NULL AUTO_INCREMENT COMMENT 'Синтетический ключ для использования в связях',
  `performerName` VARCHAR(45) NOT NULL COMMENT 'Имя исполнителя трэка. Тип VARCHAR - имя небольшое',
  PRIMARY KEY (`performerId`),
  UNIQUE INDEX `name_UNIQUE` (`performerName` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `musicstore`.`tracks`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `musicstore`.`tracks` (
  `trackId` INT NOT NULL AUTO_INCREMENT COMMENT 'Синтетический ключ для импользоания в связях',
  `name` VARCHAR(45) NOT NULL COMMENT 'Название трека. Альтернативный ключ. Тип VARCHAR - имя небольшое',
  `genre` SET('classic', 'electro', 'pop', 'rock', 'jazz', 'blues') NOT NULL COMMENT 'Жанр трека. Может быть совокупностью нескольких жанров.',
  `price` INT NOT NULL COMMENT 'Цена трека.',
  `date` DATETIME NOT NULL COMMENT 'Дата добавления трека. Является индексом, так как используется для поиска по дате',
  `performers_id` INT NOT NULL COMMENT 'Идентификатор исполнителя',
  PRIMARY KEY (`trackId`, `performers_id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC),
  INDEX `fk_tracks_performers1_idx` (`performers_id` ASC),
  INDEX `date` (`date` ASC),
  CONSTRAINT `fk_tracks_performers1`
    FOREIGN KEY (`performers_id`)
    REFERENCES `musicstore`.`performers` (`performerId`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `musicstore`.`albums`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `musicstore`.`albums` (
  `albumId` INT NOT NULL AUTO_INCREMENT COMMENT 'Синтетический ключ для использования в связях',
  `name` VARCHAR(45) NOT NULL COMMENT 'Название альбома. Альтернативный ключ',
  `genre` SET('classic', 'electro', 'pop', 'rock', 'jazz', 'blues') NOT NULL COMMENT 'Жанр альбома. Может быть сочетанием нескольких жанров.',
  `price` INT NOT NULL COMMENT 'Цена альбома',
  `date` DATETIME NOT NULL COMMENT 'Дата создания альбома. Является индексом, так как используется для поиска по дате',
  `performers_id` INT NOT NULL COMMENT 'Идентиикатор исполнителя альбома. ForeignKey',
  PRIMARY KEY (`albumId`, `performers_id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC),
  INDEX `fk_albums_performers1_idx` (`performers_id` ASC),
  INDEX `date` (`date` ASC),
  CONSTRAINT `fk_albums_performers1`
    FOREIGN KEY (`performers_id`)
    REFERENCES `musicstore`.`performers` (`performerId`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `musicstore`.`comments`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `musicstore`.`comments` (
  `commentsId` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `text` TEXT NOT NULL,
  `date` DATE NOT NULL,
  `users_userId` INT NOT NULL,
  `tracks_trackId` INT NOT NULL,
  `tracks_performers_id` INT NOT NULL,
  PRIMARY KEY (`commentsId`, `users_userId`, `tracks_trackId`, `tracks_performers_id`),
  INDEX `fk_comments_users1_idx` (`users_userId` ASC),
  INDEX `fk_comments_tracks1_idx` (`tracks_trackId` ASC, `tracks_performers_id` ASC),
  CONSTRAINT `fk_comments_users1`
    FOREIGN KEY (`users_userId`)
    REFERENCES `musicstore`.`users` (`userId`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_comments_tracks1`
    FOREIGN KEY (`tracks_trackId` , `tracks_performers_id`)
    REFERENCES `musicstore`.`tracks` (`trackId` , `performers_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `musicstore`.`assemblages`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `musicstore`.`assemblages` (
  `assemblageId` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `genre` SET('classic', 'electro', 'pop', 'rock', 'jazz', 'blues') NOT NULL,
  `price` INT NULL,
  `date` DATE NOT NULL,
  `users_userId` INT NOT NULL,
  PRIMARY KEY (`assemblageId`, `users_userId`),
  INDEX `fk_assemblages_users1_idx` (`users_userId` ASC),
  CONSTRAINT `fk_assemblages_users1`
    FOREIGN KEY (`users_userId`)
    REFERENCES `musicstore`.`users` (`userId`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `musicstore`.`albums_has_tracks`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `musicstore`.`albums_has_tracks` (
  `albums_albumId` INT NOT NULL,
  `albums_performers_id` INT NOT NULL,
  `tracks_trackId` INT NOT NULL,
  `tracks_performers_id` INT NOT NULL,
  PRIMARY KEY (`albums_albumId`, `albums_performers_id`, `tracks_trackId`, `tracks_performers_id`),
  INDEX `fk_albums_has_tracks_tracks1_idx` (`tracks_trackId` ASC, `tracks_performers_id` ASC),
  INDEX `fk_albums_has_tracks_albums1_idx` (`albums_albumId` ASC, `albums_performers_id` ASC),
  CONSTRAINT `fk_albums_has_tracks_albums1`
    FOREIGN KEY (`albums_albumId` , `albums_performers_id`)
    REFERENCES `musicstore`.`albums` (`albumId` , `performers_id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_albums_has_tracks_tracks1`
    FOREIGN KEY (`tracks_trackId` , `tracks_performers_id`)
    REFERENCES `musicstore`.`tracks` (`trackId` , `performers_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `musicstore`.`assemblages_has_tracks`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `musicstore`.`assemblages_has_tracks` (
  `assemblages_assemblagesId` INT UNSIGNED NOT NULL,
  `assemblages_users_userId` INT NOT NULL,
  `tracks_trackId` INT NOT NULL,
  `tracks_performers_id` INT NOT NULL,
  PRIMARY KEY (`assemblages_assemblagesId`, `assemblages_users_userId`, `tracks_trackId`, `tracks_performers_id`),
  INDEX `fk_assemblages_has_tracks_tracks1_idx` (`tracks_trackId` ASC, `tracks_performers_id` ASC),
  INDEX `fk_assemblages_has_tracks_assemblages1_idx` (`assemblages_assemblagesId` ASC, `assemblages_users_userId` ASC),
  CONSTRAINT `fk_assemblages_has_tracks_assemblages1`
    FOREIGN KEY (`assemblages_assemblagesId` , `assemblages_users_userId`)
    REFERENCES `musicstore`.`assemblages` (`assemblageId` , `users_userId`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_assemblages_has_tracks_tracks1`
    FOREIGN KEY (`tracks_trackId` , `tracks_performers_id`)
    REFERENCES `musicstore`.`tracks` (`trackId` , `performers_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `musicstore`.`users_has_tracks`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `musicstore`.`users_has_tracks` (
  `users_userId` INT NOT NULL,
  `tracks_trackId` INT NOT NULL,
  `tracks_performers_id` INT NOT NULL,
  PRIMARY KEY (`users_userId`, `tracks_trackId`, `tracks_performers_id`),
  INDEX `fk_users_has_tracks_tracks1_idx` (`tracks_trackId` ASC, `tracks_performers_id` ASC),
  INDEX `fk_users_has_tracks_users1_idx` (`users_userId` ASC),
  CONSTRAINT `fk_users_has_tracks_users1`
    FOREIGN KEY (`users_userId`)
    REFERENCES `musicstore`.`users` (`userId`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_users_has_tracks_tracks1`
    FOREIGN KEY (`tracks_trackId` , `tracks_performers_id`)
    REFERENCES `musicstore`.`tracks` (`trackId` , `performers_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `musicstore`.`users_has_albums`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `musicstore`.`users_has_albums` (
  `users_userId` INT NOT NULL,
  `albums_albumId` INT NOT NULL,
  `albums_performers_id` INT NOT NULL,
  PRIMARY KEY (`users_userId`, `albums_albumId`, `albums_performers_id`),
  INDEX `fk_users_has_albums_albums1_idx` (`albums_albumId` ASC, `albums_performers_id` ASC),
  INDEX `fk_users_has_albums_users1_idx` (`users_userId` ASC),
  CONSTRAINT `fk_users_has_albums_users1`
    FOREIGN KEY (`users_userId`)
    REFERENCES `musicstore`.`users` (`userId`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_users_has_albums_albums1`
    FOREIGN KEY (`albums_albumId` , `albums_performers_id`)
    REFERENCES `musicstore`.`albums` (`albumId` , `performers_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `musicstore`.`users_has_assemblages`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `musicstore`.`users_has_assemblages` (
  `users_userId` INT NOT NULL,
  `assemblages_assemblageId` INT UNSIGNED NOT NULL,
  `assemblages_users_userId` INT NOT NULL,
  PRIMARY KEY (`users_userId`, `assemblages_assemblageId`, `assemblages_users_userId`),
  INDEX `fk_users_has_assemblages_assemblages1_idx` (`assemblages_assemblageId` ASC, `assemblages_users_userId` ASC),
  INDEX `fk_users_has_assemblages_users1_idx` (`users_userId` ASC),
  CONSTRAINT `fk_users_has_assemblages_users1`
    FOREIGN KEY (`users_userId`)
    REFERENCES `musicstore`.`users` (`userId`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_users_has_assemblages_assemblages1`
    FOREIGN KEY (`assemblages_assemblageId` , `assemblages_users_userId`)
    REFERENCES `musicstore`.`assemblages` (`assemblageId` , `users_userId`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
