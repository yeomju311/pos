package pos.domainlayer.tax;

import pos.domainlayer.Money;
import pos.domainlayer.Sale;

public class TaxMasterAdapter implements ITaxCalculatorAdapter {

	TaxMaster tax = new TaxMaster();
	
	@Override
	public Money getTaxes(Sale s) {
		// TODO Auto-generated method stub
		return tax.calcTax(s);
	}

}
