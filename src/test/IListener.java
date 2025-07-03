package Info;

import java.io.IOException;

import org.testng.ITestListener;
import org.testng.ITestResult;

public class IListener extends BaseClass implements ITestListener {
	
	public void onTestSuccess(ITestResult result)
	{
		System.out.println("Test case Passed:" +result.getName());
		try {
			takeScreenshot(result.getName());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void onTestFailure(ITestResult result)
	{
		System.out.println("Test case Failed:" +result.getName());
		try {
			takeScreenshot(result.getName());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	


}

