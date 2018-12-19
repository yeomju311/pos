import pos.domainlayer.Register;
import pos.domainlayer.Store;
import pos.presentationlayer.ProcessSaleJFrame;

public class MainGUI {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		if(args.length == 1) {
			String dbFileName = args[0];
			
			Store store = new Store(dbFileName); // new Store() 할 때 DB 넣어야함..!
			Register register = store.getRegister();
			
			new ProcessSaleJFrame(register);
		}
		else {
			Store store = new Store(); 
			Register register = store.getRegister();
			
			new ProcessSaleJFrame(register);
			System.out.println("Wrong Access");
		}
	}

}
