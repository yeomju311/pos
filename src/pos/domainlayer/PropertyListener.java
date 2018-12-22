package pos.domainlayer;

// 기능8. 구매 물건이 추가시 현재 합계 갱신
public interface PropertyListener {
	public void onPropertyEvent(Sale source, String name, Money value);
}
