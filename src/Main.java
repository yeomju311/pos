import pos.domainlayer.*;

public class Main {

	public static void main(String[] args) {
		
		Store store = new Store();
		Register register = store.getRegister();
		
		Sale sale = register.makeNewSale(); //새로운 판매 시작.
		
		register.enterItem(new ItemID(100), 3);
		register.enterItem(new ItemID(200), 2);
		
		register.endSale();
		
		System.out.println("Total = " + sale.getTotal()); //Total = pos.domainlayer.Money@7852e922 -> Money클래스에 toString()추가.
		
		register.makePayment(new Money(10000));
		
		System.out.println("Balance = " + sale.getBalance());
		
	}

}
