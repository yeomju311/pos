import pos.domainlayer.Register;
import pos.domainlayer.Store;
import pos.presentationlayer.ProcessSaleJFrame;

public class MainGUI {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Store store = new Store();
		Register register = store.getRegister();
		
		new ProcessSaleJFrame(register);
	}

}
