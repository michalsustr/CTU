CREATE DATABASE osd;

CREATE TABLE ucty
(
   jmeno character varying(40) NOT NULL, 
   prijmeni character varying(40) NOT NULL, 
   cislo_uctu character varying(40) NOT NULL, 
   pocatecni_zustatek DECIMAL(10,2) NOT NULL DEFAULT 0
);

CREATE TABLE transakce
(
   cislo_uctu character varying(40) NOT NULL, 
   datum DATE NOT NULL,   
   transakce DECIMAL(10,2) NOT NULL DEFAULT 0
);

