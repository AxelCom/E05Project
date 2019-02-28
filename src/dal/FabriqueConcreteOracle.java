package dal;

public class FabriqueConcreteOracle {
	
	private static FabriqueConcreteOracle instance = null;
	
	private FabriqueConcreteOracle() {
	}
	
	public static FabriqueConcreteOracle getInstance() {
		if(instance == null) {
			instance = new FabriqueConcreteOracle();
		}
		return instance;
	}
	
	public I_ProduitDAO createConnexionI_PrdoduitOracle() {
			return new ProduitDAO_Oracle();
	}
	
	public I_CatalogueDAO createConnexionI_CatalogueOracle() {
		return new CatalogueDAO_Oracle();
}

}
