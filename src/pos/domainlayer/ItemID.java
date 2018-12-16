package pos.domainlayer;
 
public class ItemID {
	String id;
	// 문자열을 위한 생성
	public ItemID(String id) {
		this.id = id;
	}
	// int형을 위한 생성
	public ItemID(int id) {
		this.id = String.valueOf(id); // int형을 문자열로 변환해서 저장
	}
	
	public String getId() {
		return id;
	}
	
	@Override
	public String toString() {
		return id;
	}
}
