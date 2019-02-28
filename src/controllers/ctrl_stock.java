package controllers;

import metier.Catalogue;
import metier.I_Catalogue;

public class ctrl_stock 
{
	private static I_Catalogue leCata = ctrl_catalogue.getCatalogue();
	
	 public static  String AfficherStock()
	 {
		 return leCata.toString();
	 }
	 
}
