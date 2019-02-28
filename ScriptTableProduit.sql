CREATE TABLE CATALOGUES
(
nomCatalogue varchar(50) UNIQUE,
CONSTRAINT pk_Catalogues PRIMARY KEY  (nomCatalogue)
)

CREATE TABLE PRODUITSPart3
(
	nomProduit varchar(50) UNIQUE,
	prixHT float,
	qte int,
	nomCatalogue varchar(50) not null,
	CONSTRAINT fk_catalogue
	  FOREIGN KEY (nomCatalogue)
	  REFERENCES catalogues(nomCatalogue),
	CONSTRAINT pk_Produits PRIMARY KEY (nomProduit,nomCatalogue)
)
Insert into produitspart3 values ('Salade',10.5,5,'Legumes')