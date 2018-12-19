package pos.domainlayer.tax;

import pos.domainlayer.Money;
import pos.domainlayer.Sale;

public class GoodAsGoldTaxProAdapter implements ITaxCalculatorAdapter {

	GoodAsGoldTaxPro tax = new GoodAsGoldTaxPro();
	
	@Override
	public Money getTaxes(Sale s) {
		// TODO Auto-generated method stub
		return tax.getTax(s);
	}

}
