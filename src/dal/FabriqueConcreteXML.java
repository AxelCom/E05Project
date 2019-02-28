package dal;

public class FabriqueConcreteXML {
	
	private static FabriqueConcreteXML instance = null;
	
	private FabriqueConcreteXML() {
	}
	
	public static FabriqueConcreteXML getInstance() {
		if(instance == null) {
			instance = new FabriqueConcreteXML();
		}
		return instance;
	}
	
	public I_ProduitDAO createConnexionI_PrdoduitXML() {
			return new AdapterProduitDAO_XML();
	}
	
	public I_CatalogueDAO createConnexionI_CatalogueXML() {
		return new CatalogueDAO_XML();
}

}
