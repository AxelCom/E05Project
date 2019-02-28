package dal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import metier.Catalogue;
import metier.I_Catalogue;


public class CatalogueDAO_Oracle implements I_CatalogueDAO {
	private Connection cn;
	private Statement st, stNb;
	private PreparedStatement pst, pstNbProd;
	private ResultSet rs, rsNbCatalogue;
	
	public CatalogueDAO_Oracle() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url = "jdbc:oracle:thin:@162.38.222.149:1521:iut";
			String login = "commergnata";
			String mdp = "1109015674V";
			cn = DriverManager.getConnection(url, login, mdp);
			st = cn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			pst = cn.prepareStatement("SELECT nomCatalogue from Catalogues where nomCatalogue = ? order by nomCatalogue asc",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			pstNbProd = cn.prepareStatement("select count(nomProduit) from produitspart3 where nomCatalogue = ?");
			rs = st.executeQuery("SELECT nomCatalogue from Catalogues order by nomCatalogue asc");
			
			stNb = cn.createStatement();
			rsNbCatalogue = stNb.executeQuery("SELECT count(nomCatalogue) as NbCatalogue from Catalogues");
		}
		catch(ClassNotFoundException e){
			System.out.println("Problème de driver !");
		}
		catch(SQLException e) {
			System.out.println("Problème SQL : " + e);
			try{
				cn.close();
			}catch(Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	@Override
	public void ajouter(String nomCatalogue) {
		try {
			rs = st.executeQuery("SELECT nomCatalogue from Catalogues order by nomCatalogue asc");
			rs.moveToInsertRow();
			rs.updateString("nomCatalogue", nomCatalogue);
			rs.insertRow();
			rs = st.executeQuery("SELECT nomCatalogue from Catalogues order by nomCatalogue asc");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public String[] getNomsCatalogues() {
		ArrayList<String> listeCatalogues = new ArrayList<String>();
		try {
			while (rs.next()) {
				listeCatalogues.add(rs.getString("nomCatalogue"));
			}
			 rs.beforeFirst();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String tabNoms[] = new String[listeCatalogues.size()];
		tabNoms = listeCatalogues.toArray(tabNoms);
		return tabNoms;
	}
	
	@Override
	public I_Catalogue getCatalogueByNom(String nomCatalogue) {
		try {
			
			pst.setString(1, nomCatalogue);
			rs = pst.executeQuery();
			if(rs.next()) {
				gererCatalogue(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}


	@Override
	public void supprimer(String nomCatalogue) {
		try {
			pst.setString(1, nomCatalogue);
			rs = pst.executeQuery();
			if(rs.next()) {
				rs.deleteRow();
				rs = st.executeQuery("SELECT nomCatalogue from Catalogues order by nomCatalogue asc");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}

	private I_Catalogue gererCatalogue(ResultSet rs) {
		String nomCatalogue;
		I_Catalogue unCatalogue = null;
		try {
			nomCatalogue = rs.getString("nomCatalogue");
			//System.out.println(nomCatalogue);
			unCatalogue = new Catalogue(nomCatalogue);
			//System.out.println(unCatalogue.getNom());
		}catch(Exception e) {
			e.printStackTrace();
		}
		return unCatalogue;
	}
	
	private void deconnexion() {
		try {
			cn.close();
			System.out.println("Connexion Fermée !");
		}catch(Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public int getNbProduitsByCatalogue(String nomCatalogue) {
		int nbProduit = 0;
		try {
			pstNbProd.setString(1, nomCatalogue);
			rs = pstNbProd.executeQuery();
			while(rs.next()) {
				nbProduit = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return nbProduit;
	}

	@Override
	public int getNbCatalogue() {
		int nbCatalogue = 0;
		try {
			rsNbCatalogue = stNb.executeQuery("SELECT count(nomCatalogue) as NbCatalogue from Catalogues");
			while(rsNbCatalogue.next()) {
				nbCatalogue = rsNbCatalogue.getInt("NbCatalogue");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return nbCatalogue;
	}
	
}
