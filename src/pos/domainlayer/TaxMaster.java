package pos.domainlayer;

public class TaxMaster {
	// total에 세율 10%를 부과한 금액을 반환
	public Money calcTax(Sale s){
		double total = (double)s.getTotal().getAmount() * 1.1;
		return new Money((int)total);
	}
}
