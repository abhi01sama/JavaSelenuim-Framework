package jam.TestComponents;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class Retry implements IRetryAnalyzer {
 int counter = 0;
 int maxTry = 1;
	@Override
	public boolean retry(ITestResult result) {
		if(counter < maxTry) {
			counter++;
			return true;
		}
		return false;
	}
	

}
