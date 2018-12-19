package pos.domainlayer;
 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

// 판매하는 상품들의 정보를 저장하는 클래스
// 다수의 product description을 카탈로그에 저장하여 관리
public class ProductCatalog { 
	
	private Connection myConnection;
	private Statement myStatement;
	private ResultSet myResultSet;
	
	private Map<String, ProductDescription> descriptions = new HashMap<String, ProductDescription>(); 
	
	public ProductCatalog(){
		
		// 견본데이터
		ItemID id1 = new ItemID(100);
		Money price1 = new Money(1000);
		
		ItemID id2 = new ItemID(200);
		Money price2 = new Money(2000);
		
		ProductDescription desc; 
		
		desc =  new ProductDescription( id1, price1, "Product 1"); // 상품 정보 전달
		descriptions.put(id1.toString(), desc); // 상품을 descriptions에 넣기
		
		desc =  new ProductDescription( id2, price2, "Product 2");
		descriptions.put(id2.toString(), desc);
	}
	
	public ProductCatalog(String dbFileName){
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
		
		retrievePOSInfomation();
	}
	
	// db에서 상품 정보를 가져와 저장한다
	private void retrievePOSInfomation() {
		
		String itemID, description;
		int price;
		
		ProductDescription desc;
		
		try {
			myResultSet = myStatement.executeQuery("SELECT itemId, price, description FROM ProductDescriptions");
			
			while(myResultSet.next()){
				itemID = myResultSet.getString("itemId");
				price = myResultSet.getInt("price");
				description = myResultSet.getString("description");
				
				desc = new ProductDescription(new ItemID(itemID), new Money(price), description);
				descriptions.put(itemID, desc);
			}
			myResultSet.close();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public ProductDescription getProductDescription(ItemID id){	
		return descriptions.get(id.toString()); // 상품 정보 반환
	}
	
	public Set getSetItemID() {
		Set<String> SetItemID = descriptions.keySet();
		return SetItemID;
	}
}
