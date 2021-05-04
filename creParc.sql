-- DROP DATABASE parc IF EXISTS;

-- CREATE DATABASE parc;

USE parc;

DROP TABLE Installer;
DROP TABLE Logiciel;
DROP TABLE Poste;
DROP TABLE Salle;
DROP TABLE Types;
DROP TABLE Segment;

CREATE TABLE Segment(
    indIP       VARCHAR(11) PRIMARY KEY,
    nomSegment  VARCHAR(20) NOT NULL,
    etage       TINYINT(1)
)ENGINE =InnoDB;

CREATE TABLE Types (
    typeLP      VARCHAR(9) PRIMARY KEY,
    nomType     VARCHAR(20)
)ENGINE =InnoDB;

CREATE TABLE Salle (
    nSalle      VARCHAR(7) PRIMARY KEY,
    nomSalle    VARCHAR(20) NOT NULL,
    nbPoste     TINYINT(2),
    indIP       VARCHAR(11),
    FOREIGN KEY (indIP) REFERENCES Segment(indIP)
)ENGINE =InnoDB;

CREATE TABLE Poste (
    nPoste      VARCHAR(7) PRIMARY KEY,
    nomPoste    VARCHAR(20) NOT NULL,
    indIP       VARCHAR(11),
    ad          VARCHAR(3) CHECK (ad BETWEEN '000' AND '255'),
    typePoste   VARCHAR(9),
    nSalle      VARCHAR(7),
    FOREIGN KEY (indIP) REFERENCES Segment(indIP),
    FOREIGN KEY (typePoste) REFERENCES Types(typeLP)
)ENGINE =InnoDB;

CREATE TABLE Logiciel (
    nLog        VARCHAR(5) PRIMARY KEY,
    nomLog      VARCHAR(20),
    dateAch     DATETIME,
    version     VARCHAR(7),
    typeLog     VARCHAR(9),
    prix        DECIMAL(6, 2) CHECK (prix >= 0),
    FOREIGN KEY (typeLog) REFERENCES Types(typeLP)
)ENGINE =InnoDB;

CREATE TABLE Installer (
    nPoste      VARCHAR(7),
    nLog        VARCHAR(5),
    numIns      INTEGER(5) PRIMARY KEY,
    dateIns     DATETIME DEFAULT NOW(),
    delai       SMALLINT,
    FOREIGN KEY (nPoste) REFERENCES Poste(nPoste),
    FOREIGN KEY (nLog) REFERENCES Logiciel(nLog)
)ENGINE =InnoDB;