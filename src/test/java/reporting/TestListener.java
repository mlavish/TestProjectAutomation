/**
 * 
 */
package reporting;

import java.util.List;

import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.IMethodInstance;
import org.testng.IMethodInterceptor;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.Test;

public class TestListener implements ITestListener, ISuiteListener, IInvokedMethodListener, IMethodInterceptor {

	// This belongs to ISuiteListener and will execute before the Suite start

	@Override
	public void onStart(ISuite arg0) {
		Reporter.log("About to begin executing Suite " + arg0.getName(), true);
	}

	// This belongs to ISuiteListener and will execute, once the Suite is
	// finished

	@Override
	public void onFinish(ISuite arg0) {
		Reporter.log("About to end executing Suite " + arg0.getName(), true);
	}

	// This belongs to ITestListener and will execute before starting of Test
	// set/batch

	public void onStart(ITestContext arg0) {
		Reporter.log("About to begin executing Test " + arg0.getName(), true);
	}

	// This belongs to ITestListener and will execute, once the Test set/batch
	// is finished

	public void onFinish(ITestContext arg0) {
		Reporter.log("Completed executing test " + arg0.getName(), true);
	}

	// This belongs to ITestListener and will execute only when the test is pass

	public void onTestSuccess(ITestResult arg0) {
		// This is calling the printTestResults method
		printTestResults(arg0);
	}

	// This belongs to ITestListener and will execute only on the event of fail
	// test

	public void onTestFailure(ITestResult arg0) {
		// This is calling the printTestResults method
		printTestResults(arg0);
	}

	// This belongs to ITestListener and will execute before the main test start
	// (@Test)

	public void onTestStart(ITestResult arg0) {
		System.out.println("The execution of the main test starts now");
	}

	// This belongs to ITestListener and will execute only if any of the main
	// test(@Test) get skipped

	public void onTestSkipped(ITestResult arg0) {
		printTestResults(arg0);
	}

	// This is the method which will be executed in case of test pass or fail

	// This will provide the information on the test

	private void printTestResults(ITestResult result) {
		Reporter.log("Test Method resides in " + result.getTestClass().getName(), true);
		if (result.getParameters().length != 0) {
			String params = "";
			for (Object parameter : result.getParameters()) {
				params += parameter.toString() + ",";
			}
			Reporter.log("Test Method had the following parameters : " + params, true);
		}

		String status = null;
		switch (result.getStatus()) {
		case ITestResult.SUCCESS:
			status = "Pass";
			break;
		case ITestResult.FAILURE:
			status = "Failed";
			break;
		case ITestResult.SKIP:
			status = "Skipped";
		}
		Reporter.log("Test Status: " + status, true);
	}

	// This belongs to IInvokedMethodListener and will execute before every
	// method including @Before @After @Test

	public void beforeInvocation(IInvokedMethod arg0, ITestResult arg1) {

		String description = arg1.getMethod().getDescription();
		if (description == null || description.isEmpty())
			description = "";
		System.setProperty("org.uncommons.reportng.escape-output", "false");
		Reporter.log("<h3><font color='#0b47a8'> Description : </font>" + description + "</h3>");

		String textMsg = "About to begin executing following method : " + returnMethodName(arg0.getTestMethod());
		Reporter.log(textMsg, true);
	}

	// This belongs to IInvokedMethodListener and will execute after every
	// method including @Before @After @Test

	public void afterInvocation(IInvokedMethod arg0, ITestResult arg1) {
		String textMsg = "Completed executing following method : " + returnMethodName(arg0.getTestMethod());
		Reporter.log(textMsg, true);

	}

	// This will return method names to the calling function

	private String returnMethodName(ITestNGMethod method) {
		return method.getRealClass().getSimpleName() + "." + method.getMethodName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.testng.ITestListener#onTestFailedButWithinSuccessPercentage(org.
	 * testng.ITestResult)
	 */
	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// Ignore this method
	}

	@Override
	public List<IMethodInstance> intercept(List<IMethodInstance> methods, ITestContext context) {
		int methCount = methods.size();

		for (int i = 0; i < methCount; i++) {
			IMethodInstance instns = methods.get(i);
			TestReporter.testDescriptor = instns.getMethod().getConstructorOrMethod().getMethod().getAnnotation(Test.class).description();
		}

		return methods;
	}
}