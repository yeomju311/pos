package pos.domainlayer;

public class AbsoluteDiscountOverThresholdPricingStrategy implements ISalePricingStrategy{
	
	Sale s;
	float discount = (float) 0.8; // 20%
	Money threshold = new Money(20000); //20000원
	
	// total이 20,000원이 초과할 때에만 20% 할인 가격 반환
	public Money getTotal(Sale s){
		
		this.s = s;
		/*
		double total = (double)s.getTotal().getAmount();
		if(total > 20000.0){
			total *= 0.8;
		}
		return new Money((int)total);
		*/
		Money pdt = s.getPreDiscountTotal();
		
		if(pdt.getAmount() < threshold.getAmount())
			return pdt;
		else
			return new Money((int)(pdt.getAmount() * discount));
	}
}
