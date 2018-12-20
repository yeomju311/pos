package pos.domainlayer;

import java.util.ArrayList;
import java.util.List;

public abstract class CompositePricingStrategy implements ISalePricingStrategy{
	protected List<ISalePricingStrategy> strategies = new ArrayList<ISalePricingStrategy>();
	
	// 각 단일 가격 결정 정책을 추가할 때 호출 
	public void add(ISalePricingStrategy s){
		strategies.add(s);
	}
	
	public abstract Money getTotal(Sale sale);
}
