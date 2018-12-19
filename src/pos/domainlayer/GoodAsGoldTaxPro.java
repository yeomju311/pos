package pos.domainlayer;

public class GoodAsGoldTaxPro {
	// total에 세율 20%를 부과한 금액을 반환
	public Money getTax(Sale s){
		double total = (double)s.getTotal().getAmount();
		total *= 1.2;
		return new Money((int)total);
	}
}
