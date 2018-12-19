package pos.domainlayer;

// factory 패턴을 적용하여 adapter를 생성하는 책임을 가지는 ServiceFactory를 정의
public class ServicesFactory {
	
	public static ServicesFactory instance; // 정적 속성
	
	ITaxCalculatorAdapter taxCalculatorAdapter;
	
	// 정적 메소드
	public static synchronized ServicesFactory getInstance() {
		if(instance == null){
			instance = new ServicesFactory();
		}
		return instance;
	}
	
	public ITaxCalculatorAdapter getTaxCalculatorAdapter() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		if(taxCalculatorAdapter == null) {
			// 합당한 클래스를 찾기 위한 반사적 접근 방법이나 데이터 주도 접근 방법:
			// 외부 프로퍼티(property)에서 데이터를 읽어온다
			String className = System.getProperty("taxcalculator.class.name");
			taxCalculatorAdapter = (ITaxCalculatorAdapter) Class.forName(className).newInstance();
		}
		return taxCalculatorAdapter;
	}
}
