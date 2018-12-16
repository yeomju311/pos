package pos.domainlayer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Sale {
	private List<SalesLineItem> lineItems =  new ArrayList<SalesLineItem>(); 
	// 내용이 항상 같다면 특별한 것만 생성자로 관리하여 생성하고 초기값으로는 기본 설정이 더 유용
	private Date date = new Date();
	private boolean isComplete = false;
	private Payment payment;
	
	public Money getBalance()	{ // 잔돈을 거슬러주기 위한 잔액 계산
		return payment.getAmount().minus(getTotal());
	}
	
	public void becomeComplete(){ 
		isComplete = true; 
	}
	
	public boolean isComplete() {  
		return isComplete; 
	}
	
	public void makeLineItem(ProductDescription desc, int quantity)	{
		lineItems.add( new SalesLineItem(desc, quantity)); 
	}
	
	public Money getTotal() { // 합계 구하기 : 부분 합을 이용하여 총합 구하기
		Money total = new Money();
		Money subtotal = null;
		
		for(SalesLineItem lineItem : lineItems)
		{
			subtotal = lineItem.getSubtotal(); // 부분 합
			total.add(subtotal); 
		}
		return total;
	}
	
	public void makePayment(Money cashTendered) {
		payment = new Payment(cashTendered);
	}
}
