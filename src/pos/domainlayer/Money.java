package pos.domainlayer;
 
public class Money {
	int amount;
	
	public Money() {
		//this.amount = 0;
		this(0);
	}
	
	public Money(int amount) {
		this.amount = amount;
	}
	
	public int getAmount() {
		return amount;
	}

	public Money times(int quantity) {
		return new Money(amount * quantity);
	}

	public void add(Money m) {
		amount += m.getAmount();
	}

	public String toString() {
		return String.valueOf(amount);
	}

	public Money minus(Money m) {
		return new Money(amount - m.getAmount());
	}
}
