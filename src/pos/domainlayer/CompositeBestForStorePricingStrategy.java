package pos.domainlayer;

import java.util.Iterator;

public class CompositeBestForStorePricingStrategy extends CompositePricingStrategy{
	// 두 개의 단일 가격 결정 정책 중 더 비싼 가격을 반환
	@Override
	public Money getTotal(Sale sale) {
		// TODO Auto-generated method stub
		Money highestTotal = new Money(Integer.MIN_VALUE);
		
		Iterator<ISalePricingStrategy> i = strategies.iterator();
		while(i.hasNext()){
			ISalePricingStrategy strategy = i.next();
			
			Money total = strategy.getTotal(sale);
			
			highestTotal = total.max(highestTotal);
		}
		return highestTotal;
	}	
	
}