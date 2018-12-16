package pos.domainlayer;
 
import java.util.HashMap;
import java.util.Map;

//판매하는 상품들의 정보를 저장하는 클래스
//다수의 product description을 카탈로그에 저장하여 관리
public class ProductCatalog { 
	
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
	
	public ProductDescription getProductDescription(ItemID id){
		
		return descriptions.get(id.toString()); // 상품 정보 반환
		}
}
