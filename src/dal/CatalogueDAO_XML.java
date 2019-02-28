package dal;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.jdom.*;
import org.jdom.input.*;
import org.jdom.output.*;

import metier.Catalogue;
import metier.I_Catalogue;
import metier.I_Produit;
import metier.Produit;


public class CatalogueDAO_XML implements I_CatalogueDAO{
	private String uri = "C:/Catalogues.xml";
	private String uriProd = "C:/ProduitsPart3.xml";
	private Document doc;
	private Document docProd;

	public CatalogueDAO_XML() {
		SAXBuilder sdoc = new SAXBuilder();
		try {
			doc = sdoc.build(uri);
			docProd = sdoc.build(uriProd);
		} catch (Exception e) {
			System.out.println("erreur construction arbre JDOM");
		}
	}
	@Override
	public void ajouter(String nomCatalogue) {
		try {
			Element root = doc.getRootElement();
			Element catalogue = new Element("catalogue");
			catalogue.setAttribute("nom", nomCatalogue);
			root.addContent(catalogue);
			sauvegarde();
		} catch (Exception e) {
			System.out.println("erreur creer catalogue");
		}
	}
	
	@Override
	public void supprimer(String nomCatalogue) {
		try {
			Element root = doc.getRootElement();
			Element catalogue = chercheCatalogue(nomCatalogue);
			if (catalogue != null) {
				root.removeContent(catalogue);
				sauvegarde();
			}
		} catch (Exception e) {
			System.out.println("erreur supprimer catalogue");
		}
	}
	
	@Override
	public I_Catalogue getCatalogueByNom(String nom) {
		Element e = chercheCatalogue(nom);
		if (e != null)
			return new Catalogue(e.getAttributeValue("nom"));
		else
			return null;
	}
	
	@Override
	public String[] getNomsCatalogues() {

		ArrayList<String> l = new ArrayList<String>();
		try {
			Element root = doc.getRootElement();
			List<Element> lCatalogue = root.getChildren("catalogue");

			for (Element catalogue : lCatalogue) {
				String nomC = catalogue.getAttributeValue("nom");
				l.add(new Catalogue(nomC).getNom());
			}
		} catch (Exception e) {
			System.out.println("erreur lireTous tous les produits");
		}
		 String[] tabNoms = new String[l.size()];
		 tabNoms = l.toArray(tabNoms);
		return tabNoms;
	}

	private boolean sauvegarde() {
		System.out.println("Sauvegarde");
		XMLOutputter out = new XMLOutputter();
		try {
			out.output(doc, new PrintWriter(uri));
			return true;
		} catch (Exception e) {
			System.out.println("erreur sauvegarde dans fichier XML");
			return false;
		}
	}

	private Element chercheCatalogue(String nom) {
		Element root = doc.getRootElement();
		List<Element> lCatalogue = root.getChildren("catalogue");
		int i = 0;
		while (i < lCatalogue.size() && !lCatalogue.get(i).getAttributeValue("nom").equals(nom))
			i++;
		if (i < lCatalogue.size())
			return lCatalogue.get(i);
		else
			return null;
	}
	@Override
	public int getNbProduitsByCatalogue(String nomCatalogue) {
		Element root = docProd.getRootElement();
		List<Element> lProduits = root.getChildren("produit");
		int i = 0;
		int nbProd = 0;
		while (i < lProduits.size()) {
			if(lProduits.get(i).getChildText("catalogue").equals(nomCatalogue)) {
				nbProd++;
			}
				i++;
		}
		return nbProd;
	}
	@Override
	public int getNbCatalogue() {
		Element root = doc.getRootElement();
		List<Element> lCatalogue = root.getChildren("catalogue");
		return lCatalogue.size();
	}
}
