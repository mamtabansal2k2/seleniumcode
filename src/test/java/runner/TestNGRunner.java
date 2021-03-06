package runner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlInclude;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlSuite.ParallelMode;
import org.testng.xml.XmlTest;

public class TestNGRunner {
	/*TestNG
	 * XmlSuite
	 * XmlTest
	 * XmlClass
	 * XmlInclude-include method under class
	 */
	TestNG testNg;
	XmlSuite suite; // 1 suite
	List<XmlSuite> allSuites;  // all suites 
	XmlTest test;
	List<XmlTest> testCases; // all testcases under 1 suite ( this is not used until now)
	Map<String,String> testParameters;
	List<XmlClass> testClasses;
	
	public TestNGRunner(int suitThreadPoolSize) {
		System.out.println("Inside TestNGRunner constructor");
		allSuites = new ArrayList<XmlSuite>();// all suits -initialized empty
		testNg = new TestNG();
		testNg.setSuiteThreadPoolSize(suitThreadPoolSize);// parallel suits
		testNg.setXmlSuites(allSuites);
		
	}
	public void createSuite(String suiteName, boolean parallelTests) {
		suite = new XmlSuite();
		suite.setName(suiteName);
		if(parallelTests)
			suite.setParallel(ParallelMode.TESTS);
		//testCases = new ArrayList<XmlTest>();  do later
		allSuites.add(suite);
		
	}
	public void addTest(String testName) {
		System.out.println("Inside addTest");
		test = new XmlTest(suite);
		test.setName(testName);
		testParameters = new HashMap<String,String>(); // init test parameter- blank
		testClasses = new ArrayList<XmlClass>(); // empty test classes
		test.setParameters(testParameters);// blank- 0 parameters
		test.setXmlClasses(testClasses);// blank - 0 classes
		System.out.println("End of addTest");
	}
	
	public void addTestParameter(String name,String value) {
		testParameters.put(name, value);
	}
	
	public void addTestClass(String className,List<String> includedMethodNames) {
		System.out.println("Inside addTestClass");
		XmlClass testClass = new XmlClass(className);
		List<XmlInclude> classMethods = new ArrayList<XmlInclude>();
		
		int priority = 1; // we have to set priority to execute included methods in order
		
		for(String methodName:includedMethodNames) {
			XmlInclude method = new XmlInclude(methodName,priority);
			classMethods.add(method);
			priority++;
		}
		testClass.setIncludedMethods(classMethods);
		testClasses.add(testClass);
		System.out.println("End of addTestClass");
	}
	public void addListener(String listenerFile) {
		suite.addListener(listenerFile);
	}
	public void run() {
		testNg.run();
	}

}
