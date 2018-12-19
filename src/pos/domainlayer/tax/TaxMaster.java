package pos.domainlayer.tax;

import pos.domainlayer.Money;
import pos.domainlayer.Sale;

public class TaxMaster {
	// total에 세율 10%를 부과한 금액을 반환
	public Money calcTax(Sale s){
		double total = (double)s.getTotal().getAmount();
		total *= 1.1;
		return new Money((int)total);
	}
}
