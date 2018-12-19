package pos.domainlayer;

import java.util.Set;

import pos.domainlayer.tax.ITaxCalculatorAdapter;

public class Register {
	private ProductCatalog catalog;
	private Sale currentSale;
	public ITaxCalculatorAdapter taxCalculatorAdapter;
	
	public Register(ProductCatalog catalog){ // 변수 가시성을 유지
		this.catalog = catalog;
	}
	
	// Singleton
	public void initialize() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		// getInstance 호출을 통한 singleton Factory 접근
		taxCalculatorAdapter = ServicesFactory.getInstance().getTaxCalculatorAdapter();
	}
	
	public void endSale()
	{
		currentSale.becomeComplete();
	}
	
	public void enterItem(ItemID id, int quantity) // 아이템 저장
	{
		ProductDescription desc = catalog.getProductDescription(id);
		currentSale.makeLineItem(desc, quantity);
	}
	
	public Set getItemIDtoProductCatalog() {
		Set set = catalog.getSetItemID();
		return set;
	}
	
	public Sale makeNewSale()
	{
		currentSale = new Sale();
		return currentSale;
	}
	
	public void makePayment(Money cashTendered) //System operation.
	{
		currentSale.makePayment(cashTendered);
	}
}
