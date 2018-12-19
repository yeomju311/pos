package pos.domainlayer;

public class GoodAsGoldTaxProAdapter implements ITaxCalculatorAdapter {

	GoodAsGoldTaxPro tax = new GoodAsGoldTaxPro();
	
	@Override
	public Money getTaxes(Sale s) {
		// TODO Auto-generated method stub
		return tax.getTax(s);
	}

}
