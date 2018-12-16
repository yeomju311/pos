package pos.presentationlayer;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import pos.domainlayer.ItemID;
import pos.domainlayer.Money;
import pos.domainlayer.Register;
import pos.domainlayer.Sale;

public class ProcessSaleJFrame extends JFrame {
	 
	Register register;
	Sale sale;
	
	JButton b_makeNewSale;
	
	JTextField t_itemID;
	JTextField t_quantity;
	JButton b_enterItem;
	
	JButton b_endSale;
	
	JTextField t_total;
	JTextField t_amount;
	JButton b_makePayment;
	
	JTextField t_balance;
	
	
	public ProcessSaleJFrame(Register register) {
		super("POS System v0.2");
		
		this.register = register;
		
		// 화면 구성
		buildGUI();
		registerEventHandler();
		
		// 화면 구성 후 pack, setVisible
		this.pack();
		this.setSize(200, 350);
		this.setVisible(true);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void buildGUI() {

		// 0.
		JLabel l_class = new JLabel("학번:20141121");
		l_class.setBounds(10, 5, 100, 20);
		JLabel l_name = new JLabel("이름:최윤주");
		l_name.setBounds(120, 5, 100, 20);
		
		// 1.
		b_makeNewSale = new JButton("1. Make New Sale()"); // 자동 import = ctrl + shift + O
		b_makeNewSale.setBounds(10, 30, 180, 35);
		
		JLabel l_itemID = new JLabel("Item ID : ");
		l_itemID.setBounds(10, 65, 70, 30);
		t_itemID = new JTextField(10);
		t_itemID.setBounds(80, 65, 110, 30);
		
		JLabel l_quantity = new JLabel("Quantity : "); 
		l_quantity.setBounds(10, 95, 70, 30);
		t_quantity = new JTextField(10);
		t_quantity.setBounds(80, 95, 110, 30);
		
		b_enterItem = new JButton("2. Enter Item()"); 
		b_enterItem.setBounds(10, 125, 180, 35);
		
		b_endSale = new JButton("3. End Sale()");
		b_endSale.setBounds(10, 160, 180, 35);
		
		JLabel l_total = new JLabel("Total : ");
		l_total.setBounds(10, 195, 70, 30);
		t_total = new JTextField(10);
		t_total.setBounds(80, 195, 110, 30);
		
		JLabel l_amount = new JLabel("Amount : ");
		l_amount.setBounds(10, 225, 70, 30);
		t_amount = new JTextField(10);
		t_amount.setBounds(80, 225, 110, 30);
		
		b_makePayment = new JButton("4. Make Payment()");
		b_makePayment.setBounds(10, 255, 180, 35);
		
		JLabel l_balance = new JLabel("Balance : ");
		l_balance.setBounds(10, 290, 70, 30);
		t_balance = new JTextField(10);
		t_balance.setBounds(80, 290, 110, 30);
		
		t_itemID.setEnabled(false);
		t_quantity.setEnabled(false);
		b_enterItem.setEnabled(false);
		b_endSale.setEnabled(false);
		t_total.setEnabled(false);
		t_amount.setEnabled(false);
		b_makePayment.setEnabled(false);
		t_balance.setEnabled(false);
		
		// 구성
		Container cp = this.getContentPane();
		cp.setLayout(null);
		
		cp.add(l_class);
		cp.add(l_name);
		
		cp.add(b_makeNewSale);

		cp.add(l_itemID);
		cp.add(t_itemID);
		cp.add(l_quantity);
		cp.add(t_quantity);
		
		cp.add(b_enterItem);
		
		cp.add(b_endSale);
		
		cp.add(l_total);
		cp.add(t_total);
		
		cp.add(l_amount);
		cp.add(t_amount);
		
		cp.add(b_makePayment);
		
		cp.add(l_balance);
		cp.add(t_balance);
		
	}
	
	private void registerEventHandler() {
		b_makeNewSale.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				sale = register.makeNewSale();
				
				t_total.setText("");
				t_amount.setText("");
				t_balance.setText("");
				
				t_itemID.setEnabled(true);
				t_quantity.setEnabled(true);
				b_enterItem.setEnabled(true);
				b_makeNewSale.setEnabled(false);
				
				System.out.println("Status: Make New Sale 버튼이 눌려졌습니다.");
			}
			
		});
		
		b_enterItem.addActionListener(enterItemHandler);	
		b_endSale.addActionListener(endSaleHandler);
		b_makePayment.addActionListener(makePaymentHandler);
	}
	
	ActionListener enterItemHandler = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			ItemID id = new ItemID(t_itemID.getText());
			int q = 0;
			
			//int q = Integer.parseInt(t_quantity.getText());
			try {
				q = Integer.parseInt(t_quantity.getText());
			} 
			catch (NumberFormatException numberFromatException) {
				JOptionPane.showMessageDialog(null, "please input only number");
			}
			
			register.enterItem(id, q);
			
			t_itemID.setText("");
			t_quantity.setText("");
			
			b_endSale.setEnabled(true);
			
			System.out.println("Status: Enter Item 버튼이 눌려졌습니다.");
		}
	};
	
	ActionListener endSaleHandler = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			register.endSale();

			System.out.println(sale.isComplete()); // register -> currentSale -> isComplete 접근 가능
			
			//TODO t_total에 총 가격이 뜨도록 한다
			t_total.setText(sale.getTotal().toString());
			
			t_itemID.setEnabled(false);
			t_quantity.setEnabled(false);
			b_enterItem.setEnabled(false);
			b_endSale.setEnabled(false);
			t_amount.setEnabled(true);
			b_makePayment.setEnabled(true);
			
			System.out.println("Status: End Sale 버튼이 눌려졌습니다.");
		}
	};
	
	ActionListener makePaymentHandler = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			int amount = Integer.parseInt(t_amount.getText());
			
			sale.makePayment(new Money(amount));
			t_balance.setText(sale.getBalance().toString());
			
			t_amount.setEnabled(false);
			b_makePayment.setEnabled(false);
			b_makeNewSale.setEnabled(true);
			
			System.out.println("Status: Make Payment 버튼이 눌려졌습니다.");
		}
	};
}
