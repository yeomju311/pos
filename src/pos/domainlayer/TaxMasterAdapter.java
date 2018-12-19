package pos.domainlayer;

public class TaxMasterAdapter implements ITaxCalculatorAdapter {

	TaxMaster tax = new TaxMaster();
	
	@Override
	public Money getTaxes(Sale s) {
		// TODO Auto-generated method stub
		return tax.calcTax(s);
	}

}
