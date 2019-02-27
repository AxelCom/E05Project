CREATE TABLE PRODUITS
(
	nomProduit varchar(50) UNIQUE,
	prixHT float,
	qte int,
	CONSTRAINT pk_Produits PRIMARY KEY (nomProduit)
)
