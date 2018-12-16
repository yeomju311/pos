import pos.domainlayer.*;

public class Main {

	public static void main(String[] args) {
		
		Store store = new Store();
		Register register = store.getRegister();
		
		Sale sale = register.makeNewSale(); //���ο� �Ǹ� ����.
		
		register.enterItem(new ItemID(100), 3);
		register.enterItem(new ItemID(200), 2);
		
		register.endSale();
		
		System.out.println("Total = " + sale.getTotal()); //Total = pos.domainlayer.Money@7852e922 -> MoneyŬ������ toString()�߰�.
		
		register.makePayment(new Money(10000));
		
		System.out.println("Balance = " + sale.getBalance());
		
	}

}
