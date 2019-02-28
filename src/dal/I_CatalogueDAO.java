package dal;

import java.util.ArrayList;

import metier.I_Catalogue;

public interface I_CatalogueDAO {

	public void ajouter(String nomCatalogue);

	public void supprimer(String nomCatalogue);

	public String[] getNomsCatalogues();

	public I_Catalogue getCatalogueByNom(String nomCatalogue);

	public int getNbProduitsByCatalogue(String nomCatalogue);

	public int getNbCatalogue();
}
