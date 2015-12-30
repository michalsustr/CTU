DROP DATABASE IF EXISTS osd;
CREATE DATABASE osd
  WITH ENCODING='UTF8';

-- switch to newly created database osd
\c osd

CREATE TABLE ucty
(
   jmeno character varying(40) NOT NULL, 
   prijmeni character varying(40) NOT NULL, 
   cislo_uctu character varying(40) NOT NULL, 
   pocatecni_zustatek double precision NOT NULL DEFAULT 0, 
   CONSTRAINT uq_cislo_uctu UNIQUE (cislo_uctu)
) WITH (
  OIDS = FALSE
);

CREATE TABLE transakce
(
   cislo_uctu character varying(40) NOT NULL, 
   datum DATE NOT NULL DEFAULT NOW(),   
   transakce double precision NOT NULL DEFAULT 0, 
   CONSTRAINT fk_cislo_uctu FOREIGN KEY (cislo_uctu) REFERENCES ucty (cislo_uctu) ON UPDATE NO ACTION ON DELETE NO ACTION
) 
WITH (
  OIDS = FALSE
)

