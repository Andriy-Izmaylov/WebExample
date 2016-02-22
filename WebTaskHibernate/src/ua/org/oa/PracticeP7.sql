CREATE DATABASE  IF NOT EXISTS `BaseDataAtt` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci */;
USE `BaseDataAtt`;

DROP TABLE IF EXISTS `User_Music_Type`;
DROP TABLE IF EXISTS `Music_Type`;
DROP TABLE IF EXISTS `Adress`;
DROP TABLE IF EXISTS `User`;
DROP TABLE IF EXISTS `Role`;

CREATE TABLE `Role`(
`IDrole` int(20) unsigned NOT NULL AUTO_INCREMENT,
`Role_Name` varchar(100) DEFAULT NULL,
PRIMARY KEY (`IDrole`)
);

CREATE TABLE `Adress`(
 `Adress_ID` int(20) unsigned NOT NULL AUTO_INCREMENT,
 `Country` varchar(250) DEFAULT NULL,
 `street` varchar(250) DEFAULT NULL,
 `ZipCode` int(20) DEFAULT NULL,
 PRIMARY KEY (`Adress_ID`)
 );


CREATE TABLE `User`(
 `id` int(20) unsigned NOT NULL AUTO_INCREMENT,
 `login` varchar(16) DEFAULT NULL,
 `password` varchar(20) DEFAULT NULL,
 `First_Name` varchar(100) DEFAULT NULL,
 `Last_Name` varchar(100) DEFAULT NULL,
 `Age` int(11) unsigned DEFAULT NULL,
 `roleID` int(20) unsigned  NULL, 
 `Address_ID` int (20) unsigned NULL,
  PRIMARY KEY (`id`),  
  FOREIGN KEY (`roleID`) REFERENCES `Role`(`IDrole`),
  FOREIGN KEY (`Address_ID`) REFERENCES `Adress` (`Adress_ID`) 
  );
 
 
 
 CREATE TABLE `Music_Type`(
`Type_ID` int(20) unsigned NOT NULL AUTO_INCREMENT,
`Type_Name` varchar(30) DEFAULT NULL,
 PRIMARY KEY (`Type_ID`) 
 );
 
 CREATE TABLE `User_Music_Type`(
 `user_Id` int (20) unsigned NOT NULL ,
 `music_type_Id` int (20) unsigned NOT NULL ,
 FOREIGN KEY (`user_Id`) REFERENCES `User`(`id`),
 FOREIGN KEY (`music_type_Id`) REFERENCES `Music_Type`(`Type_ID`)
 ); 

 
 

 
 INSERT INTO Role (Role_Name) VALUES ('USER'),('MODERATOR'),('ADMIN');
 INSERT INTO Music_Type (Type_Name) VALUES ('Rock'),('Alternative'),('Classic');
 
 

 