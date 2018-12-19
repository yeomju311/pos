package pos.presentationlayer;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.*;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import pos.domainlayer.ItemID;
import pos.domainlayer.Money;
import pos.domainlayer.Register;
import pos.domainlayer.Sale;

public class ProcessSaleJFrame extends JFrame {
	
	private Connection myConnection;
	private Statement myStatement;
	private ResultSet myResultSet;
	 
	Register register;
	Sale sale;
	
	JButton b_makeNewSale;
	
	JComboBox cb_itemID;
	JTextField t_quantity;
	JTextField t_description;
	
	JButton b_enterItem;
	JTextField t_currentTotal;
	
	JButton b_endSale;
	
	JRadioButton rb_taxMaster;
	JRadioButton rb_goodAsGoldTaxPro;
	
	JButton b_calculateTax;
	JTextField t_totalTax;
	
	JRadioButton rb_forCus;
	JRadioButton rb_forStore;
	
	JButton b_applyDiscount;
	JTextField t_totalDiscount;
	JTextField t_amount;
	
	JButton b_makePayment;
	
	JTextField t_balance;
	
	JTextArea textArea;
	
	public ProcessSaleJFrame(Register register) {
		super("POS System (학번 : 20141121 이름 : 최윤주)");
		
		this.register = register;
		
		// 화면 구성
		buildGUI();
		registerEventHandler();
		
		// 화면 구성 후 pack, setVisible
		this.pack();
		this.setSize(650, 550);
		this.setVisible(true);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public ProcessSaleJFrame(Register register, String dbFileName) {
		super("POS System (학번 : 20141121 이름 : 최윤주)");
		
		// db에 연결
		try {
			// connect to database
			myConnection = DriverManager.getConnection("jdbc:ucanaccess://"+ dbFileName);
			   
			// create Statement for executing SQL
			myStatement = myConnection.createStatement();
			} 
		catch (SQLException exception) {
			exception.printStackTrace();
		}
		
		this.register = register;
		
		// 화면 구성
		buildGUI();
		registerEventHandler();
		
		// 화면 구성 후 pack, setVisible
		this.pack();
		this.setSize(650, 550);
		this.setVisible(true);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void buildGUI() {

		// 1.
		b_makeNewSale = new JButton("1. Make New Sale()"); // 자동 import = ctrl + shift + O
		b_makeNewSale.setBounds(50, 10, 200, 35); //x, y, width, height
		
		JLabel l_itemID = new JLabel("Item ID : ");
		l_itemID.setBounds(65, 40, 70, 30);
		cb_itemID = new JComboBox();
		cb_itemID.setBounds(135, 40, 110, 30);
		
		JLabel l_quantity = new JLabel("Quantity : "); 
		l_quantity.setBounds(65, 70, 70, 30);
		t_quantity = new JTextField(10);
		t_quantity.setBounds(135, 70, 110, 30);
		
		JLabel l_description = new JLabel("Description : ");
		l_description.setBounds(50, 100, 90, 30);
		t_description = new JTextField(10);
		t_description.setBounds(135, 100, 110, 30);
		
		b_enterItem = new JButton("2. Enter Item()"); 
		b_enterItem.setBounds(50, 130, 200, 35);
		
		JLabel l_currentTotal = new JLabel("Current Total : ");
		l_currentTotal.setBounds(50, 165, 100, 30);
		t_currentTotal = new JTextField(10);
		t_currentTotal.setBounds(140, 165, 110, 30);
		
		b_endSale = new JButton("3. End Sale()");
		b_endSale.setBounds(50, 195, 200, 35);
		
		ButtonGroup bg1 = new ButtonGroup();
		rb_taxMaster = new JRadioButton("TaxMaster");
		rb_taxMaster.setBounds(20, 230, 100, 30);
		bg1.add(rb_taxMaster);
		rb_goodAsGoldTaxPro = new JRadioButton("GoodAsGoldTaxPro");
		rb_goodAsGoldTaxPro.setBounds(120, 230, 160, 30);
		bg1.add(rb_goodAsGoldTaxPro);
		
		b_calculateTax = new JButton("4. calculateTax()");
		b_calculateTax.setBounds(50, 260, 200, 35);
		
		JLabel l_totalTax = new JLabel("Total with Tax : ");
		l_totalTax.setBounds(45, 295, 120, 30);
		t_totalTax = new JTextField(10);
		t_totalTax.setBounds(145, 295, 110, 30);
		
		ButtonGroup bg2 = new ButtonGroup();
		rb_forCus = new JRadioButton("BestForCustomer");
		rb_forCus.setBounds(20, 325, 140, 30);
		bg2.add(rb_forCus);
		rb_forStore = new JRadioButton("BestForStore");
		rb_forStore.setBounds(160, 325, 120, 30);
		bg2.add(rb_forStore);
		
		b_applyDiscount = new JButton("5. applyDiscount()");
		b_applyDiscount.setBounds(50, 355, 200, 35);
		
		JLabel l_totalDiscount = new JLabel("Total after Discount : ");
		l_totalDiscount.setBounds(30, 390, 140, 30);
		t_totalDiscount = new JTextField(10);
		t_totalDiscount.setBounds(160, 390, 110, 30);
		
		JLabel l_amount = new JLabel("Amount : ");
		l_amount.setBounds(65, 420, 70, 30);
		t_amount = new JTextField(10);
		t_amount.setBounds(135, 420, 110, 30);
		
		b_makePayment = new JButton("6. Make Payment()");
		b_makePayment.setBounds(50, 450, 200, 35);
		
		JLabel l_balance = new JLabel("Balance : ");
		l_balance.setBounds(65, 480, 70, 30);
		t_balance = new JTextField(10);
		t_balance.setBounds(135, 485, 110, 30);
		
		textArea = new JTextArea();
		//textArea.setBounds(300, 10, 300, 500);
		
		JScrollPane scroll = new JScrollPane(textArea);
		scroll.setBounds(300, 10, 300, 500);
		/*
		JScrollPane scroll = new JScrollPane(textArea,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
				*/
		
		cb_itemID.setEnabled(false);
		t_quantity.setEnabled(false);
		b_enterItem.setEnabled(false);
		b_endSale.setEnabled(false);
		//t_total.setEnabled(false);
		t_amount.setEnabled(false);
		b_makePayment.setEnabled(false);
		t_balance.setEnabled(false);
		
		// 구성
		Container cp = this.getContentPane();
		cp.setLayout(null);
		
		cp.add(b_makeNewSale);

		cp.add(l_itemID);
		cp.add(cb_itemID);
		cb_itemID.addItem("");
		loadProduct();
		// JComboBox 연결
		cb_itemID.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				itemIDJComboBoxItemStateChanged(e);
			}
			
		});
		
		cp.add(l_quantity);
		cp.add(t_quantity);
		cp.add(l_description);
		cp.add(t_description);
		
		cp.add(b_enterItem);
		cp.add(l_currentTotal);
		cp.add(t_currentTotal);
		
		cp.add(b_endSale);
		
		cp.add(rb_taxMaster);
		cp.add(rb_goodAsGoldTaxPro);
		
		cp.add(b_calculateTax);
		cp.add(l_totalTax);
		cp.add(t_totalTax);
		
		cp.add(rb_forCus);
		cp.add(rb_forStore);
		
		cp.add(b_applyDiscount);
		
		cp.add(l_totalDiscount);
		cp.add(t_totalDiscount);
		
		cp.add(l_amount);
		cp.add(t_amount);
		
		cp.add(b_makePayment);
		
		cp.add(l_balance);
		cp.add(t_balance);
		
		cp.add(scroll);
		
	}
	
	private void itemIDJComboBoxItemStateChanged(ItemEvent e) {
		if(( e.getStateChange() == ItemEvent.SELECTED ) && ( cb_itemID.getSelectedIndex() != 0 )){
			
		}
	}
	
	private void registerEventHandler() {
		/*
		b_makeNewSale.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				sale = register.makeNewSale();
				
				//t_total.setText("");
				t_amount.setText("");
				t_balance.setText("");
				
				cb_itemID.setEnabled(true);
				t_quantity.setEnabled(true);
				b_enterItem.setEnabled(true);
				b_makeNewSale.setEnabled(false);
				
				System.out.println("Status: Make New Sale 버튼이 눌려졌습니다.");
			}
			
		});
		*/
		
		b_makeNewSale.addActionListener(makeNewSale);
		b_enterItem.addActionListener(enterItemHandler);	
		b_endSale.addActionListener(endSaleHandler);
		b_makePayment.addActionListener(makePaymentHandler);
	}
	
	ActionListener makeNewSale = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			//t_total.setText("");
			t_amount.setText("");
			t_balance.setText("");
			
			cb_itemID.setEnabled(true);
			t_quantity.setEnabled(true);
			b_enterItem.setEnabled(true);
			b_makeNewSale.setEnabled(false);
			
			System.out.println("Status: Make New Sale 버튼이 눌려졌습니다.");
		}
		
	};
	
	ActionListener enterItemHandler = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			//ItemID id = new ItemID(cb_itemID.getText());
			int q = 0;
			
			//int q = Integer.parseInt(t_quantity.getText());
			try {
				q = Integer.parseInt(t_quantity.getText());
			} 
			catch (NumberFormatException numberFromatException) {
				JOptionPane.showMessageDialog(null, "please input only number");
			}
			
			//register.enterItem(id, q);
			
			//cb_itemID.setText("");
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
			//t_total.setText(sale.getTotal().toString());
			
			cb_itemID.setEnabled(false);
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
	
	
	// db에서 상품 정보 가져와서 JComboBox에 보여줌
	private void loadProduct() {
		try {
			myResultSet = myStatement.executeQuery("SELECT itemid FROM ProductDescriptions");
			
			while(myResultSet.next()) {
				cb_itemID.addItem(myResultSet.getString("itemid"));
			}
			
			myResultSet.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	

}
