package pos.domainlayer;

//각종 클래스 생성 책임을 갖고 있다
public class Store {
	private ProductCatalog catalog = new ProductCatalog();
	private Register register = new Register(catalog); 
	
	public Register getRegister() { return register; }
}
