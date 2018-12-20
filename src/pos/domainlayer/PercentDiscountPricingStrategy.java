package pos.domainlayer;

public class PercentDiscountPricingStrategy implements ISalePricingStrategy{
	
	public Sale s;
	float percentage = (float) 0.9;
	
	// total의 10% 할인 가격 반환
	public Money getTotal(Sale s){
		this.s = s;

		return new Money((int)(s.getPreDiscountTotal().getAmount() * percentage));
	}
}
