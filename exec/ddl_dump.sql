-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema ssafy_web_db
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema ssafy_web_db
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `ssafy_web_db` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci ;
USE `ssafy_web_db` ;

-- -----------------------------------------------------
-- Table `ssafy_web_db`.`member`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ssafy_web_db`.`member` (
  `member_id` BIGINT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(30) NOT NULL,
  `password` VARCHAR(150) NOT NULL,
  `role` VARCHAR(20) NOT NULL DEFAULT 'USER',
  `nickname` VARCHAR(45) NOT NULL,
  `introduction` VARCHAR(100) NULL DEFAULT NULL,
  `report` INT UNSIGNED NOT NULL DEFAULT '0',
  PRIMARY KEY (`member_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 73
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci
COMMENT = '회원 테이블';


-- -----------------------------------------------------
-- Table `ssafy_web_db`.`study`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ssafy_web_db`.`study` (
  `study_id` BIGINT NOT NULL AUTO_INCREMENT,
  `category` VARCHAR(45) NOT NULL,
  `study_name` VARCHAR(45) NOT NULL,
  `created_date` DATETIME NOT NULL,
  `modified_date` DATETIME NOT NULL,
  `dismantle_flag` CHAR(1) NOT NULL DEFAULT 'F',
  `dismantle_date` DATETIME NULL DEFAULT NULL,
  PRIMARY KEY (`study_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 58
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci
COMMENT = '스터디 테이블';


-- -----------------------------------------------------
-- Table `ssafy_web_db`.`article`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ssafy_web_db`.`article` (
  `article_id` BIGINT NOT NULL AUTO_INCREMENT,
  `member_id` BIGINT NOT NULL,
  `study_id` BIGINT NULL DEFAULT NULL,
  `category` VARCHAR(45) NOT NULL,
  `title` VARCHAR(150) NOT NULL,
  `content` TEXT NULL DEFAULT NULL,
  `created_date` TIMESTAMP NULL DEFAULT NULL,
  `modified_date` TIMESTAMP NULL DEFAULT NULL,
  `hit` INT UNSIGNED NOT NULL DEFAULT '0',
  `dislike` INT UNSIGNED NOT NULL DEFAULT '0',
  `good` INT UNSIGNED NULL DEFAULT NULL,
  PRIMARY KEY (`article_id`),
  INDEX `FK_article_member_id_member_member_id` (`member_id` ASC) VISIBLE,
  INDEX `FK_article_study_id_study_study_id` (`study_id` ASC) VISIBLE,
  CONSTRAINT `FK_article_member_id_member_member_id`
    FOREIGN KEY (`member_id`)
    REFERENCES `ssafy_web_db`.`member` (`member_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `FK_article_study_id_study_study_id`
    FOREIGN KEY (`study_id`)
    REFERENCES `ssafy_web_db`.`study` (`study_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 75
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci
COMMENT = '게시판에 작성한 글 테이블';


-- -----------------------------------------------------
-- Table `ssafy_web_db`.`article_evaluation`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ssafy_web_db`.`article_evaluation` (
  `article_evaluation_id` BIGINT NOT NULL AUTO_INCREMENT,
  `member_id` BIGINT NOT NULL,
  `article_id` BIGINT NOT NULL,
  `evaluation` CHAR(1) NULL DEFAULT NULL,
  `created_date` TIMESTAMP NULL DEFAULT NULL,
  `modified_date` TIMESTAMP NULL DEFAULT NULL,
  PRIMARY KEY (`article_evaluation_id`),
  INDEX `member_id` (`member_id` ASC) VISIBLE,
  INDEX `article_id` (`article_id` ASC) VISIBLE,
  CONSTRAINT `article_evaluation_ibfk_1`
    FOREIGN KEY (`member_id`)
    REFERENCES `ssafy_web_db`.`member` (`member_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `article_evaluation_ibfk_2`
    FOREIGN KEY (`article_id`)
    REFERENCES `ssafy_web_db`.`article` (`article_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 24
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;


-- -----------------------------------------------------
-- Table `ssafy_web_db`.`comment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ssafy_web_db`.`comment` (
  `comment_id` BIGINT NOT NULL AUTO_INCREMENT,
  `member_id` BIGINT NULL DEFAULT NULL,
  `article_id` BIGINT NOT NULL,
  `content` VARCHAR(500) NULL DEFAULT NULL,
  `modified_date` TIMESTAMP NULL DEFAULT NULL,
  `created_date` TIMESTAMP NULL DEFAULT NULL,
  PRIMARY KEY (`comment_id`),
  INDEX `FK_comment_article_id_article_article_id` (`article_id` ASC) VISIBLE,
  INDEX `FK_comment_member_id_member_member_id` (`member_id` ASC) VISIBLE,
  CONSTRAINT `FK_comment_article_id_article_article_id`
    FOREIGN KEY (`article_id`)
    REFERENCES `ssafy_web_db`.`article` (`article_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `FK_comment_member_id_member_member_id`
    FOREIGN KEY (`member_id`)
    REFERENCES `ssafy_web_db`.`member` (`member_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 49
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci
COMMENT = '게시판에 작성된 댓글 테이블';


-- -----------------------------------------------------
-- Table `ssafy_web_db`.`meeting`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ssafy_web_db`.`meeting` (
  `meeting_id` BIGINT NOT NULL AUTO_INCREMENT,
  `study_id` BIGINT NOT NULL,
  `created_date` DATETIME NOT NULL,
  `modified_date` DATETIME NOT NULL,
  `run_flag` CHAR(1) NOT NULL DEFAULT 'T',
  `meeting_name` VARCHAR(50) NULL DEFAULT NULL,
  PRIMARY KEY (`meeting_id`),
  INDEX `FK_meeting_study_id_study_study_id` (`study_id` ASC) VISIBLE,
  CONSTRAINT `FK_meeting_study_id_study_study_id`
    FOREIGN KEY (`study_id`)
    REFERENCES `ssafy_web_db`.`study` (`study_id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT)
ENGINE = InnoDB
AUTO_INCREMENT = 68
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci
COMMENT = '화상회의 정보 테이블';


-- -----------------------------------------------------
-- Table `ssafy_web_db`.`file`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ssafy_web_db`.`file` (
  `file_id` BIGINT NOT NULL AUTO_INCREMENT,
  `member_id` BIGINT NULL DEFAULT NULL,
  `article_id` BIGINT NULL DEFAULT NULL,
  `meeting_id` BIGINT NULL DEFAULT NULL,
  `study_id` BIGINT NULL DEFAULT NULL,
  `created_date` DATETIME NOT NULL,
  `modified_date` DATETIME NULL DEFAULT NULL,
  `file_name` VARCHAR(150) NOT NULL,
  `file_path` VARCHAR(200) NOT NULL,
  `file_origin_name` VARCHAR(100) NOT NULL,
  `content_type` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`file_id`),
  INDEX `member_id` (`member_id` ASC) VISIBLE,
  INDEX `article_id` (`article_id` ASC) VISIBLE,
  INDEX `meeting_id` (`meeting_id` ASC) VISIBLE,
  INDEX `study_id` (`study_id` ASC) VISIBLE,
  CONSTRAINT `file_ibfk_1`
    FOREIGN KEY (`member_id`)
    REFERENCES `ssafy_web_db`.`member` (`member_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `file_ibfk_2`
    FOREIGN KEY (`article_id`)
    REFERENCES `ssafy_web_db`.`article` (`article_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `file_ibfk_3`
    FOREIGN KEY (`meeting_id`)
    REFERENCES `ssafy_web_db`.`meeting` (`meeting_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `file_ibfk_4`
    FOREIGN KEY (`study_id`)
    REFERENCES `ssafy_web_db`.`study` (`study_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 170
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;


-- -----------------------------------------------------
-- Table `ssafy_web_db`.`study_member`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ssafy_web_db`.`study_member` (
  `study_member_id` BIGINT NOT NULL AUTO_INCREMENT,
  `member_id` BIGINT NOT NULL,
  `study_id` BIGINT NOT NULL,
  `leader_flag` CHAR(1) NOT NULL DEFAULT 'F',
  `accepted_flag` CHAR(1) NOT NULL DEFAULT 'F',
  `created_date` DATETIME NOT NULL,
  `modified_date` DATETIME NOT NULL,
  PRIMARY KEY (`study_member_id`),
  INDEX `FK_study_member_study_id_study_study_id` (`study_id` ASC) VISIBLE,
  INDEX `FK_study_member_member_id_member_member_id` (`member_id` ASC) VISIBLE,
  CONSTRAINT `FK_study_member_member_id_member_member_id`
    FOREIGN KEY (`member_id`)
    REFERENCES `ssafy_web_db`.`member` (`member_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `FK_study_member_study_id_study_study_id`
    FOREIGN KEY (`study_id`)
    REFERENCES `ssafy_web_db`.`study` (`study_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 198
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci
COMMENT = '스터디에 가입한 멤버 테이블';


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
