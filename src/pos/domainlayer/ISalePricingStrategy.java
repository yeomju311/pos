package pos.domainlayer;

public interface ISalePricingStrategy {
	//public Sale sale;
	public Money getTotal(Sale s);
}
