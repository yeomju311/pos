import pos.domainlayer.Register;
import pos.domainlayer.Store;
import pos.presentationlayer.ProcessSaleJFrame;

public class MainGUI {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		if(args.length == 1) {
			String dbFileName = args[0];
			
			Store store = new Store();
			Register register = store.getRegister();
			
			new ProcessSaleJFrame(register, dbFileName);
		}
		else {
			System.out.println("Wrong Access");
		}
	}

}
