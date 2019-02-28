package controllers;

import java.util.ArrayList;

import dal.FabriqueConcreteOracle;
import dal.FabriqueConcreteXML;
import dal.FabriqueDAO;
import dal.I_CatalogueDAO;
import dal.I_ProduitDAO;
import metier.I_Catalogue;

public class ctrl_catalogue 
{
	private static FabriqueDAO dao = FabriqueConcreteOracle.getInstance();
	private static I_CatalogueDAO connexionCatalogue = dao.CreateCatalogueDAO();
	private static I_ProduitDAO connexionProduits = dao.CreateProduitsDAO(); 
	private static I_Catalogue leCata = null;
	
	 public static String[] AfficherNomsCatalogues()
	 {
		 return connexionCatalogue.getNomsCatalogues();
	 }
	 
	 public static int getNbProduitsByCatalogue(String nomCatalogue) {
		 return connexionCatalogue.getNbProduitsByCatalogue(nomCatalogue);
	 }
	 
	 public static void setCatalogue(String nom) {
		 leCata = connexionCatalogue.getCatalogueByNom(nom);
		 leCata.addProduits(connexionProduits.getProduits());
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
		 return connexionCatalogue.getNbCatalogue();
	 }
	 
	 public static void supprimerCatalogue(String nom) {
		 connexionCatalogue.supprimer(nom);
	 }
	 
	 public static void ajouterCatalogue(String nom) {
		 connexionCatalogue.ajouter(nom);
	 }
	 public static I_ProduitDAO getConnexionProduit() {
		 return connexionProduits;
	 }
	 
}
