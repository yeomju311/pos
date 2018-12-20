package pos.domainlayer;

import java.util.Iterator;

public class CompositeBestForCustomerPricingStrategy extends CompositePricingStrategy{
	// 두 개의 단일 가격 결정 정책 중 더 싼 가격을 반환
	@Override
	public Money getTotal(Sale sale) {
		// TODO Auto-generated method stub
		Money lowestTotal = new Money(Integer.MAX_VALUE);
		
		Iterator<ISalePricingStrategy> i = strategies.iterator();
		while(i.hasNext()){
			ISalePricingStrategy strategy = i.next();
			
			Money total = strategy.getTotal(sale);
			
			lowestTotal = total.min(lowestTotal);
		}
		return lowestTotal;
	}
	
}
