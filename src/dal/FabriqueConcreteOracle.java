package dal;

public class FabriqueConcreteOracle implements FabriqueDAO{
	
	private static FabriqueConcreteOracle instance = null;
	
	private FabriqueConcreteOracle() {
	}
	
	public static FabriqueConcreteOracle getInstance() {
		if(instance == null) {
			instance = new FabriqueConcreteOracle();
		}
		return instance;
	}

	@Override
	public I_CatalogueDAO CreateCatalogueDAO() {
		return new CatalogueDAO_Oracle();
		
	}

	@Override
	public I_ProduitDAO CreateProduitsDAO() {
		return new ProduitDAO_Oracle();
		
	}

}
