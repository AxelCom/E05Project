package dal;

public interface FabriqueDAO {

	public abstract I_CatalogueDAO CreateCatalogueDAO();
	public abstract I_ProduitDAO CreateProduitsDAO();

}
