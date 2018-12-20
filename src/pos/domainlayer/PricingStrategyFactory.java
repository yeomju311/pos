package pos.domainlayer;

public class PricingStrategyFactory {
	public static PricingStrategyFactory instance;
	ISalePricingStrategy strategy;
	
	public static PricingStrategyFactory getInstance() {
		if(instance == null){
			instance = new PricingStrategyFactory();
		}
		return instance;
	}
	
	public ISalePricingStrategy getSalePricingStrategy() throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		String className = System.getProperty("salepricingstrategy.class.name");
		strategy = (ISalePricingStrategy) Class.forName(className).newInstance();
		return strategy;
	}
}
