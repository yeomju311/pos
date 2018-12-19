package pos.domainlayer.tax;

import pos.domainlayer.Money;
import pos.domainlayer.Sale;

public interface ITaxCalculatorAdapter {
	Money getTaxes(Sale s);
}
