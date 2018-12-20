package pos.domainlayer;

public interface PropertyListener {
	public void onPropertyEvent(Sale source, String name, Money value);
}
