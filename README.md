GSO-Drive

What you need to get this project running.
1) Install SBT on your PC
2) Create mysql database named "gso_drive"
3) Then copy and paste the script below into database "gso_drive":

CREATE TABLE Benutzer(
ID INT AUTO_INCREMENT PRIMARY KEY,
Vorname VARCHAR(30),
Nachname VARCHAR(30),
Geschlecht VARCHAR(15),
Schulform VARCHAR(15),
istFahrer BOOLEAN,
Email VARCHAR(60),
AltEmail VARCHAR(60),
Handy VARCHAR(20),
Passwort VARCHAR(50));

CREATE TABLE Fahrer(
ID_Benutzer INT PRIMARY KEY,
Geschlaechts_Pref INT,
Fuehrerschein_seit DATETIME,
Raucherwagen BOOLEAN,
AllergikerAuto BOOLEAN,
AutoModell VARCHAR(200));

CREATE TABLE SpezFahrt(
ID INT AUTO_INCREMENT PRIMARY KEY,
Datum DATETIME,
ID_MetaFahrt INT
);

CREATE TABLE MetaFahrt(
ID INT AUTO_INCREMENT PRIMARY KEY,
Fahrer INT,
Anmerkung VARCHAR(500),
Anfahrt BOOLEAN,
Stadt VARCHAR(40),
Stadtteil VARCHAR(40),
Hausnummer VARCHAR(5),
Strasse VARCHAR(40),
PLZ int,
Preis decimal);
