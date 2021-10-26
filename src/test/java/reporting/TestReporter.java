package reporting;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.xml.XmlSuite;

import tests.BaseTest;

public class TestReporter implements IReporter {

	public static String testDescriptor;
	private ITestResult itResultFail;
	private LinkedList<String> result;
	String ssDiv;

	@Override
	public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Map<String, List<String>> testWiseResult = new LinkedHashMap<String, List<String>>();
		String testSuite = suites.get(0).getName();
		testSuite = testSuite + "|" + new SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
				.format(suites.get(0).getResults().values().iterator().next().getTestContext().getStartDate());
		System.out.println(testSuite);
		Map<String, Map<String, List<String>>> suiteWiseResult = new LinkedHashMap<String, Map<String, List<String>>>();
		for (ISuite suite : suites) {
			System.out.println("suiteName ==> " + suite.getName());
			Map<String, ISuiteResult> suiteResults = suite.getResults();
			for (ISuiteResult sr : suiteResults.values()) {
				List<ITestNGMethod> i = Arrays.asList(sr.getTestContext().getAllTestMethods());
				i.forEach(a -> {
					testWiseResult.put(a.getTestClass().getName().split("tests.")[1] + "-" + a.getMethodName() + ":"
							+ a.getDescription(), new LinkedList<>());
				});
				ITestContext tc = sr.getTestContext();
				Set<ITestResult> passedSet = new LinkedHashSet<ITestResult>();
				passedSet.addAll(tc.getPassedTests().getAllResults());
				ITestResult itResultPass;
				ITestNGMethod iTestNGMethodPass;
				for (Iterator<ITestResult> it = passedSet.iterator(); it.hasNext();) {
					result = new LinkedList<>();
					itResultPass = it.next();
					String params = "";
					if (itResultPass.getParameters().length != 0) {
						params = "_" + itResultPass.getParameters()[0].toString();
					}
					result.add("PASS");
					result.add(String.valueOf((itResultPass.getEndMillis() - itResultPass.getStartMillis()) / 1000));
					result.add("N/A");
					iTestNGMethodPass = itResultPass.getMethod();
					result.add("N/A");
					testWiseResult.put(itResultPass.getTestClass().getName().split("tests.")[1] + "-"
							+ iTestNGMethodPass.getMethodName() + params + ":" + iTestNGMethodPass.getDescription()
							+ params, result);
				}

				Set<ITestResult> failedSet = new LinkedHashSet<ITestResult>();
				failedSet.addAll(tc.getFailedTests().getAllResults());
				ITestNGMethod iTestNGMethodFail;
				for (Iterator<ITestResult> it = failedSet.iterator(); it.hasNext();) {
					ssDiv = "";
					result = new LinkedList<>();
					itResultFail = it.next();
					String params = "";
					if (itResultFail.getParameters().length != 0) {
						params = "_" + itResultFail.getParameters()[0].toString();
					}
					result.add("FAIL");
					result.add(String.valueOf((itResultFail.getEndMillis() - itResultFail.getStartMillis()) / 1000));
					String message = "";
					try {
						message = itResultFail.getThrowable().toString().replaceAll("System info:.*", "")
								.replaceAll("Build info:.*", "").replaceAll("Driver info:.*", "")
								.replaceAll("Capabilities.*", "").replaceAll("Session.*", "")
								.replaceAll("The following asserts failed:|expected \\[[^,\\r\\n]+\\]", "")
								.replace("java.lang.AssertionError:", "");
					} catch (Exception e) {
						message = itResultFail.getThrowable().toString();
					}
					System.out.println("Assertion failure messages=>" + message);
					result.add(message);
					iTestNGMethodFail = itResultFail.getMethod();
					Map<String, String> testMethodSS = BaseTest.allFailScreenshots
							.get(iTestNGMethodFail.getMethodName() + params);
					if (testMethodSS == null) {
						ssDiv = ssDiv + "----";
					} else if (!testMethodSS.isEmpty()) {
						testMethodSS.forEach((k, v) -> {
							ssDiv = ssDiv + "<br><br><a href='" + v + "'>" + k + "</a>";
						});
					} else {
						ssDiv = ssDiv + "----";
					}
					result.add(ssDiv);
					testWiseResult.put(iTestNGMethodFail.getTestClass().getName().split("tests.")[1] + "-"
							+ iTestNGMethodFail.getMethodName() + params + ":" + iTestNGMethodFail.getDescription(),
							result);
				}

				Set<ITestResult> failedConfigurationSet = new HashSet<ITestResult>();
				failedConfigurationSet.addAll(tc.getFailedConfigurations().getAllResults());
				ITestResult itResultFailConf;
				ITestNGMethod iTestNGMethodFailConf;
				for (Iterator<ITestResult> it = failedConfigurationSet.iterator(); it.hasNext();) {
					result = new LinkedList<>();
					itResultFailConf = it.next();
					result.add("FAIL");
					result.add(String
							.valueOf((itResultFailConf.getEndMillis() - itResultFailConf.getStartMillis()) / 1000));
					result.add("Configration Failure");
					iTestNGMethodFailConf = itResultFailConf.getMethod();
					result.add("N/A, It was a configuration failure for the suite.");
					testWiseResult.put(iTestNGMethodFailConf.getTestClass().getName().split("tests.")[1] + "-"
							+ iTestNGMethodFailConf.getMethodName() + ":" + iTestNGMethodFailConf.getDescription(),
							result);

				}

				Set<ITestResult> skippedSet = new HashSet<ITestResult>();
				skippedSet.addAll(tc.getSkippedTests().getAllResults());
				skippedSet.addAll(tc.getSkippedConfigurations().getAllResults());
				ITestResult itResultSkip;
				ITestNGMethod iTestNGMethodSkip;
				for (Iterator<ITestResult> it = skippedSet.iterator(); it.hasNext();) {
					result = new LinkedList<>();
					itResultSkip = it.next();
					result.add("SKIP");
					result.add(String.valueOf((itResultSkip.getEndMillis() - itResultSkip.getStartMillis()) / 1000));
					iTestNGMethodSkip = itResultSkip.getMethod();
					result.add("N/A");
					testWiseResult.put(
							iTestNGMethodSkip.getTestClass().getName().split("tests.")[1] + "-"
									+ iTestNGMethodSkip.getMethodName() + ":" + iTestNGMethodSkip.getDescription(),
							result);
				}
			}
			suiteWiseResult.put(testSuite, testWiseResult);
		}
		generateHTMLReport(testSuite, suiteWiseResult);
	}

	public void generateHTMLReport(String suiteName, Map<String, Map<String, List<String>>> suiteWiseResult) {
		try {
			File file = new File("HTMLReport.html");
			File failureFile = new File("FailureReport.html");
			FileWriter f = new FileWriter(file, false);
			BufferedWriter bw = new BufferedWriter(f);
			FileWriter ff = new FileWriter(failureFile, false);
			BufferedWriter fw = new BufferedWriter(ff);

			bw.write("<html>");
			if (suiteWiseResult.size() > 0) {
				for (Entry<String, Map<String, List<String>>> e : suiteWiseResult.entrySet()) {

					bw.write("<title>Test Report:" + e.getKey() + "</title>");
					String testSuiteName = e.getKey().split("\\|")[0];
					String testSuiteStart = e.getKey().split("\\|")[1];
					String message1 = "<head><p style='padding-left: 30px;'><span style='color: #333333;'>" + "<br>"
							+ "Hi. Please find the execution report below:" + "<br> </span></p>"
							+ "<h1 style='text-align: center;'><span style='text-decoration: underline;'><em>"
							+ testSuiteName + "</em></span></h1><h3 style='text-align: center;'> Started at:"
							+ testSuiteStart + "</h3></head>";
					bw.write(message1);
					fw.write(message1);
					String message2 = "<table border='2' width='100%'><tbody><tr style='height: 40px; background-color: #e7f064;'>"
							+ "<th style='text-align: center;'  width='200'><strong>Test Case Name</strong></th>"
							+ "<th style='text-align: center;'  width='200'><strong>Test Case Description</strong></th>"
							+ "<th style='text-align: center;' width='50'><strong>Result</strong></th>"
							+ "<th style='text-align: center;' width='200'><strong>Reason of Failure (If any)</strong></th>"
							+ "<th style='text-align: center;' width='200'><strong>Screenshot</strong></th>" + "</tr>";
					bw.write(message2);
					fw.write(message2);
					Map<String, List<String>> suite = e.getValue();

					boolean isFailPresent = suite.entrySet().stream()
							.anyMatch(entry -> entry.getValue().contains("FAIL"))
							|| suite.entrySet().stream().anyMatch(entry -> entry.getValue().contains("SKIP"));
					System.out.println("Were there any failures: " + isFailPresent);

					for (Entry<String, List<String>> entry : suite.entrySet()) {
						try {
							String testName = entry.getKey().split(":")[0];
							String testDescription = entry.getKey().split(":")[1];
							List<String> values = entry.getValue();
							String result = values.get(0);
							String message = values.get(2);
							String screenShot = values.get(3);
							String message3 = "<tr><td style='height: 20px; text-align: left; padding-left: 5px' width='200'>"
									+ testName + "</td>"
									+ "<td style='height: 20px; text-align: left; padding-left: 5px' width='50'>"
									+ testDescription + "</td>"
									+ "<td style='height: 20px; text-align: left; padding-left: 5px' width='50'>"
									+ result + "</td>"
									+ "<td style='height: 20px; text-align: left; padding-left: 5px' width='200'>"
									+ message + "</td>"
									+ "<td style='height: 20px; text-align: left; padding-left: 5px' width='200'>"
									+ screenShot + "</td>" + "</tr>";
							bw.write(message3);
							if (result.equalsIgnoreCase("Fail") || result.equalsIgnoreCase("Skip")) {
								fw.write(message3);
							}
						} catch (Exception ex) {
							// do nothing
						}
					}
					bw.write("</table>");
					fw.write("</table>");
				}
			}
			bw.write("</body></html>");
			fw.write("</body></html>");
			bw.close();
			fw.close();
			f.close();
			ff.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getHostName() {
		InetAddress ip;
		String hostname = null;
		try {
			ip = InetAddress.getLocalHost();
			hostname = ip.getHostName();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return hostname;
	}
}