package pos.domainlayer;
 
//각종 클래스 생성 책임을 갖고 있다
public class Store {
	
	private ProductCatalog catalog;
	private Register register;
	
	public Store() {
		catalog = new ProductCatalog();
		register = new Register(catalog); 
	}
	
	public Store(String dbFileName){
		catalog = new ProductCatalog(dbFileName);
		register = new Register(catalog);
	}
	
	public Register getRegister() { return register; }
}
