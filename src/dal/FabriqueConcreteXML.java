package dal;

public class FabriqueConcreteXML implements FabriqueDAO{
	
	private static FabriqueConcreteXML instance = null;
	
	private FabriqueConcreteXML() {
	}
	
	public static FabriqueConcreteXML getInstance() {
		if(instance == null) {
			instance = new FabriqueConcreteXML();
		}
		return instance;
	}
	
	@Override
	public I_CatalogueDAO CreateCatalogueDAO() {
		return new CatalogueDAO_XML();
	}

	@Override
	public I_ProduitDAO CreateProduitsDAO() {
		return new AdapterProduitDAO_XML();	
	}

}
