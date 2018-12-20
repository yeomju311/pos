package pos.domainlayer;

import java.util.Set;

public class Register {
	private ProductCatalog catalog;
	private Sale currentSale;
	
	public Register(ProductCatalog catalog){ // 변수 가시성을 유지
		this.catalog = catalog;
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
	
	// 기능6. 세금 계산 기능 지원
	public void calculateTax() {
		ITaxCalculatorAdapter tax = null;

		try {
			tax = ServicesFactory.getInstance().getTaxCalculatorAdapter();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		currentSale.setTotalWithTax(tax.getTaxes(currentSale)); // 현재 sale 객체의 total을 tax 적용 후 total로 변경해준다
	}

	// 기능7. 다양한 가격 결정 정책 지원
	public void applyDiscount(CompositePricingStrategy compositePricingStrategy) {
		currentSale.setCompositePriciingStrategy(compositePricingStrategy);
		
		currentSale.getCompositePricingStrategy().add(new PercentDiscountPricingStrategy());
		currentSale.getCompositePricingStrategy().add(new AbsoluteDiscountOverThresholdPricingStrategy());
	}
	
	public void makePayment(Money cashTendered) //System operation.
	{
		currentSale.makePayment(cashTendered);
	}
}
