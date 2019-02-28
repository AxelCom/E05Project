package controllers;

import java.util.ArrayList;

import dal.FabriqueConcreteOracle;
import dal.I_CatalogueDAO;
import metier.Catalogue;
import metier.I_Catalogue;
import vues.FenetrePrincipale;

public class ctrl_catalogue 
{
	private static I_CatalogueDAO cataDAO = FabriqueConcreteOracle.getInstance().createConnexionI_CatalogueOracle();
	private static I_Catalogue leCata = null;
	
	 public static String[] AfficherNomsCatalogues()
	 {
		 return cataDAO.getNomsCatalogues();
	 }
	 
	 public static int getNbProduitsByCatalogue(String nomCatalogue) {
		 return cataDAO.getNbProduitsByCatalogue(nomCatalogue);
	 }
	 
	 public static FenetrePrincipale setCatalogue(String nom) {
		 leCata = cataDAO.getCatalogueByNom(nom);
		 return new FenetrePrincipale();
	 }
	 
	 public static I_Catalogue getCatalogue() {
		 return leCata;
	 }
	 
	 public static String[] afficherDetailsCatalogues() {
		 ArrayList<String> listDetail = new ArrayList<String>();
		 for (String nomCatalogue : AfficherNomsCatalogues()) {
			 String text = nomCatalogue + " : " + getNbProduitsByCatalogue(nomCatalogue) + " produits";
			 listDetail.add(text);
		}
		 String tabDetails[] = new String[listDetail.size()];
		 tabDetails = listDetail.toArray(tabDetails);
		 return tabDetails;
	 }
	 
	 public static int getNbCatalogue() {
		 return cataDAO.getNbCatalogue();
	 }
	 
	 public static void supprimerCatalogue(String nom) {
		 cataDAO.supprimer(nom);
	 }
	 
	 public static void ajouterCatalogue(String nom) {
		 cataDAO.ajouter(nom);;
	 }
	 
}
