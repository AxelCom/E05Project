package dal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import controllers.ctrl_catalogue;
import metier.I_Produit;
import metier.Produit;


public class ProduitDAO_Oracle implements I_ProduitDAO {
	private Connection cn;
	private Statement st;
	private PreparedStatement pst, pstCata;
	private ResultSet rs;
	
	public ProduitDAO_Oracle() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url = "jdbc:oracle:thin:@162.38.222.149:1521:iut";
			String login = "commergnata";
			String mdp = "1109015674V";
			cn = DriverManager.getConnection(url, login, mdp);
			st = cn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			pst = cn.prepareStatement("SELECT nomProduit, prixHT, qte, nomCatalogue from ProduitsPart3 where nomProduit = ? order by nomProduit asc",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			pstCata = cn.prepareStatement("SELECT nomProduit, prixHT, qte, nomCatalogue from ProduitsPart3 where nomCatalogue = ? order by nomProduit asc",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			rs = st.executeQuery("SELECT nomProduit, prixHT, qte, nomCatalogue from ProduitsPart3 order by nomProduit asc");
			//System.out.println(ctrl_catalogue.getCatalogue().getNom());
		}
		catch(ClassNotFoundException e){
			System.out.println("Probl�me de driver !");
		}
		catch(SQLException e) {
			System.out.println("Probl�me SQL : " + e);
			try{
				cn.close();
			}catch(Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	@Override
	public void ajouter(I_Produit produit) {
		try {
			
			rs.moveToInsertRow();
			rs.updateString("nomProduit", produit.getNom());
			rs.updateDouble("prixHT", produit.getPrixUnitaireHT());
			rs.updateInt("qte", produit.getQuantite());
			rs.updateString("nomCatalogue", ctrl_catalogue.getCatalogue().getNom());
			rs.insertRow();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public List<I_Produit> getProduits() {
		List<I_Produit> listeProduits = new ArrayList<I_Produit>();
		try {
			pstCata.setString(1, ctrl_catalogue.getCatalogue().getNom());
			rs = pstCata.executeQuery();
			while (rs.next()) {
				listeProduits.add(gererProduit(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listeProduits;
	}
	
	@Override
	public I_Produit getProduitByNom(String nomProduit) {
		try {
			
			pst.setString(1, nomProduit);
			rs = pst.executeQuery();
			if(rs.next()) {
				gererProduit(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void modifier(I_Produit produit) {
		try {
			pst.setString(1, produit.getNom());
			rs = pst.executeQuery();
			if(rs.next()) {
				rs.updateDouble("qte", produit.getQuantite());
				rs.updateRow();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void supprimer(I_Produit produit) {
		try {
			pst.setString(1, produit.getNom());
			rs = pst.executeQuery();
			if(rs.next()) {
				rs.deleteRow();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}

	private I_Produit gererProduit(ResultSet rs) {
		String nomProduit;
		double prixHT;
		int qte;
		I_Produit unProduit = null;
		try {
			nomProduit = rs.getString("nomProduit");
			prixHT = rs.getDouble("prixHT");
			qte = rs.getInt("qte");
			unProduit = new Produit(nomProduit,prixHT,qte);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return unProduit;
	}
	
	private void deconnexion() {
		try {
			cn.close();
			System.out.println("Connexion Ferm�e !");
		}catch(Exception e) {
			e.printStackTrace();
		}

	}
	
}
