package pos.presentationlayer;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.Set;

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

import pos.domainlayer.AbsoluteDiscountOverThresholdPricingStrategy;
import pos.domainlayer.CompositeBestForCustomerPricingStrategy;
import pos.domainlayer.CompositeBestForStorePricingStrategy;
import pos.domainlayer.ISalePricingStrategy;
import pos.domainlayer.ITaxCalculatorAdapter;
import pos.domainlayer.ItemID;
import pos.domainlayer.Money;
import pos.domainlayer.PercentDiscountPricingStrategy;
import pos.domainlayer.PropertyListener;
import pos.domainlayer.Register;
import pos.domainlayer.Sale;
import pos.domainlayer.ServicesFactory;

public class ProcessSaleJFrame extends JFrame implements PropertyListener, ActionListener{
	
	// 단계적 활성화용 작업 단계 상수 지정
	private static final int MAKE_NEW_SALE = 1; 
	private static final int ENTER_ITEM = 2;
	private static final int END_SALE = 3;
	private static final int CALCULATE_TAX = 4;
	private static final int APPLY_DISCOUNT = 5;
	private static final int MAKE_PAYMENT = 6;
	private int action;
	private boolean flag = true; // try catch 시 사용
	
	// db 연결
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
	
	JTextArea ta_status;
	
	public ProcessSaleJFrame(Register register, String dbFileName) {
		super("POS System (학번 : 20141121 이름 : 최윤주)");
		
		this.register = register;
		
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
		
		// 화면 구성
		buildGUI();
		registerEventHandler(this);
		loadProductOnJComboBox();
		
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
		
		b_enterItem = new JButton("2. Enter Item() (반복)"); 
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
		
		ta_status = new JTextArea();
		
		JScrollPane scroll = new JScrollPane(ta_status);
		scroll.setBounds(300, 10, 300, 500);
		
		cb_itemID.setEnabled(false);
		t_quantity.setEnabled(false);
		t_description.setEnabled(false);
		b_enterItem.setEnabled(false);
		t_currentTotal.setEnabled(false);
		b_endSale.setEnabled(false);
		rb_taxMaster.setEnabled(false);
		rb_goodAsGoldTaxPro.setEnabled(false);
		b_calculateTax.setEnabled(false);
		t_totalTax.setEnabled(false);
		rb_forCus.setEnabled(false);
		rb_forStore.setEnabled(false);
		b_applyDiscount.setEnabled(false);
		t_totalDiscount.setEnabled(false);
		t_amount.setEnabled(false);
		b_makePayment.setEnabled(false);
		t_balance.setEnabled(false);
		ta_status.setEnabled(false);
		
		// 구성
		Container cp = this.getContentPane();
		cp.setLayout(null);
		
		cp.add(b_makeNewSale);

		cp.add(l_itemID);
		cp.add(cb_itemID);
		cb_itemID.addItem("");
		
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
		
		// actionListener 등록 - 라디오버튼이 눌릴 때마다 상태변화를 알리기 위함
		rb_taxMaster.addActionListener(this);
		rb_goodAsGoldTaxPro.addActionListener(this);
		rb_forCus.addActionListener(this);
		rb_forStore.addActionListener(this);
	}
	
	private void itemIDJComboBoxItemStateChanged(ItemEvent e) {
		// 콤보박스에서 아이템이 클릭되고 && 선택된 인덱스가 0이 아니면
		if((e.getStateChange() == ItemEvent.SELECTED) && (cb_itemID.getSelectedIndex() != 0)){
			// enterItem 버튼 활성화
			b_enterItem.setEnabled(true);
			
			// ta_status에 상품 정보 띄움
			String itemId = (String)cb_itemID.getSelectedItem();
			String description = null;
			int price = 0;
			
			try{
				myResultSet = myStatement.executeQuery("SELECT description, price FROM ProductDescriptions WHERE itemId = '"+itemId + "'");
				
				if(myResultSet.next()){
					description = myResultSet.getString("description");
					price = myResultSet.getInt("price");
				}
				myResultSet.close();
			}
			catch(SQLException exception){
				exception.printStackTrace();
			}
			
			ta_status.append("description : "+description+", price : " + price + "\n");
		}
	}
	
	private void registerEventHandler(PropertyListener pl) {
		
		b_makeNewSale.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				action = MAKE_NEW_SALE;
				
				sale = register.makeNewSale();
				sale.addPropertyListener(pl); // 기능8. 구매 물건이 추가시 현재 합계 갱신
				
				controlComponent(action);
			}
			
		});
		
		b_enterItem.addActionListener(enterItemHandler);	
		b_endSale.addActionListener(endSaleHandler);
		b_calculateTax.addActionListener(calculateTax);
		b_applyDiscount.addActionListener(applyDiscount);
		b_makePayment.addActionListener(makePaymentHandler);
	}

	ActionListener enterItemHandler = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			action = ENTER_ITEM;
			
			int q = 0;
			
			// 기능 1. Quantity 입력란 오류 처리
			try {
				q = Integer.parseInt(t_quantity.getText());
				flag = true;
			} 
			catch (NumberFormatException numberFromatException) {
				JOptionPane.showMessageDialog(null, "please input only number");
				t_quantity.setText("");
				t_quantity.requestFocusInWindow();
				flag = false; 
			}
			
			if(flag) { // quantity 입력이 올바를 때만 enterItem이 진행
				// register
				register.enterItem(new ItemID((String)cb_itemID.getSelectedItem()), q);
				
				// itemId값으로 db에서 정보 얻어온 뒤 t_description에 상품 정보 띄움
				String itemId = (String)cb_itemID.getSelectedItem();
				String description = null;
				
				try{
					myResultSet = myStatement.executeQuery("SELECT description FROM ProductDescriptions WHERE itemId = '"+itemId + "'");
					
					if(myResultSet.next()){
						description = myResultSet.getString("description");
					}
					myResultSet.close();
				}
				catch(SQLException exception){
					exception.printStackTrace();
				}
				
				t_description.setText(description);
				
				// 기능8. 구매 물건이 추가시 현재 합계 갱신 - observer
				sale.setTotal(sale.getTotal());
				
				controlComponent(action);
			}
		}
	};
	
	ActionListener endSaleHandler = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			action = END_SALE;
			
			// register
			register.endSale();
			
			controlComponent(action);
		}
	};
	
	ActionListener calculateTax = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			action = CALCULATE_TAX;

			// 라디오 버튼이 어떤 것이 선택되었느냐에 따라 tax률이 다르게 매겨짐
			if(rb_taxMaster.isSelected()){
				// TaxMaster
				System.setProperty("taxcalculator.class.name", "pos.domainlayer.TaxMasterAdapter");
				ta_status.append("TaxMaster : 세금률 10%가 적용되었습니다\n");
				flag = true;
			}
			else if(rb_goodAsGoldTaxPro.isSelected()){
				// GoodAsGoldTaxPro
				System.setProperty("taxcalculator.class.name", "pos.domainlayer.GoodAsGoldTaxProAdapter");
				ta_status.append("GoodAsGoldTaxPro : 세금률 20%가 적용되었습니다\n");
				flag = true;
			}
			else { // 아무것도 선택하지 않았을 때
				JOptionPane.showMessageDialog(null, "please select anything");
				rb_taxMaster.doClick(); // 임의로 첫번째 라디오버튼 클릭되게 함
				flag = false;
			}
			
			if(flag){
				// register
				register.calculateTax();
				t_totalTax.setText(sale.getTotalWithTax().toString());
	
				controlComponent(action);
			}
		}
		
	};
	
	ActionListener applyDiscount = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			action = APPLY_DISCOUNT;
			
			//ISalePricingStrategy pricingStrategy = null;
			
			if(rb_forCus.isSelected()){
				// BestForCustomer
				register.applyDiscount(new CompositeBestForCustomerPricingStrategy());
				flag = true;
			}
			else if(rb_forStore.isSelected()){
				// BestForStore
				register.applyDiscount(new CompositeBestForStorePricingStrategy());
				flag = true;
			}
			else { // 아무것도 선택하지 않았을 때
				JOptionPane.showMessageDialog(null, "please select anything");
				rb_forCus.doClick(); // 임의로 첫번째 라디오버튼 클릭되게 함
				flag = false;
			}
			
			if(flag){
				t_totalDiscount.setText(sale.getAfterDiscountTotal().toString());
				
				controlComponent(action);
			}
		}
		
	};
	
	ActionListener makePaymentHandler = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			action = MAKE_PAYMENT;
			
			int amount = 0;
			
			// 기능 1. Quantity 입력란 오류 처리
			try {
				amount = Integer.parseInt(t_amount.getText());
				flag = true;
			} 
			catch (NumberFormatException numberFromatException) {
				JOptionPane.showMessageDialog(null, "please input only number");
				t_amount.setText("");
				t_amount.requestFocusInWindow();
				flag = false;
			}
			
			if(flag) {
				sale.makePayment(new Money(amount));
				t_balance.setText(sale.getBalance().toString());
			
				controlComponent(action);
			}
		}
	};
	
	// ProductDescription에서 상품 정보 가져와서 JComboBox에 보여줌
	private void loadProductOnJComboBox() {
		Set set = register.getItemIDtoProductCatalog();
		Iterator<String> it = set.iterator();
		while(it.hasNext()){
			String item = it.next();
			cb_itemID.addItem(item);
		}
	}

	@Override
	public void onPropertyEvent(Sale source, String name, Money value) {
		// TODO Auto-generated method stub
		if (name.equals("sale.total"))
			t_currentTotal.setText(value.toString());
	}
	
	public void controlComponent(int action) {
		switch(action) {
		case MAKE_NEW_SALE:
			t_quantity.setText("");
			t_description.setText("");
			t_amount.setText("");
			t_balance.setText("");
			t_currentTotal.setText("");
			t_totalTax.setText("");
			t_totalDiscount.setText("");
			cb_itemID.setEnabled(true);
			t_quantity.setEnabled(true);
			//b_makeNewSale.setEnabled(false);
			b_enterItem.setEnabled(false);
			b_endSale.setEnabled(false);
			rb_taxMaster.setSelected(false); // 선택된 것 취소하는 메소드?
			rb_taxMaster.setEnabled(false);
			rb_goodAsGoldTaxPro.setSelected(false);
			rb_goodAsGoldTaxPro.setEnabled(false);
			b_calculateTax.setEnabled(false);
			rb_forCus.setSelected(false);
			rb_forCus.setEnabled(false);
			rb_forStore.setSelected(false);
			rb_forStore.setEnabled(false);
			b_applyDiscount.setEnabled(false);
			t_amount.setEnabled(false);
			b_makePayment.setEnabled(false);
			ta_status.append("Status: Make New Sale 버튼이 눌려졌습니다.\n");
			ta_status.append("Item ID를 선택 후 수량을 입력해주세요\n");
			break;
		case ENTER_ITEM:
			t_quantity.setText("");
			cb_itemID.setSelectedIndex(0);
			b_endSale.setEnabled(true);
			ta_status.append("Status: Enter Item 버튼이 눌려졌습니다.\n");
			break;
		case END_SALE:
			cb_itemID.setEnabled(false);
			t_quantity.setEnabled(false);
			b_enterItem.setEnabled(false);
			b_endSale.setEnabled(false);
			rb_taxMaster.setEnabled(true);
			rb_goodAsGoldTaxPro.setEnabled(true);
			b_calculateTax.setEnabled(true);
			ta_status.append("Status: End Sale 버튼이 눌려졌습니다.\n");
			ta_status.append("사용하고자 하는 세금 방법을 선택해주세요\n");
			break;
		case CALCULATE_TAX:
			rb_taxMaster.setEnabled(false);
			rb_goodAsGoldTaxPro.setEnabled(false);
			b_calculateTax.setEnabled(false);
			rb_forCus.setEnabled(true);
			rb_forStore.setEnabled(true);
			b_applyDiscount.setEnabled(true);
			ta_status.append("Status: Calculate Tax 버튼이 눌려졌습니다.\n");
			ta_status.append("사용하고자 하는 가격 정책을 선택해주세요\n");
			break;
		case APPLY_DISCOUNT:
			rb_forCus.setEnabled(false);
			rb_forStore.setEnabled(false);
			b_applyDiscount.setEnabled(false);
			t_amount.setEnabled(true);
			b_makePayment.setEnabled(true);
			ta_status.append("Status: Apply Discount 버튼이 눌려졌습니다.\n");
			ta_status.append("지불할 금액을 입력해주세요\n");
			break;
		case MAKE_PAYMENT:
			t_amount.setEnabled(false);
			b_makePayment.setEnabled(false);
			b_makeNewSale.setEnabled(true);
			ta_status.append("Status: Make Payment 버튼이 눌려졌습니다.\n");
			ta_status.append("잔돈을 확인해주세요\n");
			break;
		default:
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == rb_taxMaster)
			ta_status.append("TaxMaster : 총 금액에 세율 10% 부과\n");
		else if(e.getSource() == rb_goodAsGoldTaxPro)
			ta_status.append("GoodAsGoldTaxPro : 총 금액에 세율 20% 부과\n");
		else if(e.getSource() == rb_forCus)
			ta_status.append("BestForCustomer : 고객에게 유리한 할인률 적용\n");
		else if(e.getSource() == rb_forStore)
			ta_status.append("BestForStore : 상점에 유리한 할인률 적용\n");
	}
}
