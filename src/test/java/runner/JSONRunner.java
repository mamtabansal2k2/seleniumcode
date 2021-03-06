package runner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONRunner {
// jsonstring,jsonarray,jsonobject
	public static void main(String[] args) throws FileNotFoundException, IOException, ParseException {
		
		Map<String,String> classMethods = new DataUtil().loadClassMethods();  // makes a new instance but does not give it a reference
		
		String path = System.getProperty("user.dir")+"//src//test//resources//jsons//testconfig.json";
		JSONParser parser = new JSONParser();
		JSONObject json = (JSONObject) parser.parse(new FileReader(new File(path)));
		JSONArray browsers = (JSONArray) json.get("browsers");
		String parallelsuites =  (String) json.get("parallelsuites");
		TestNGRunner testNG = new TestNGRunner(Integer.parseInt(parallelsuites));
		
		JSONArray testsuites = (JSONArray) json.get("testsuites");
		for(int bId=0;bId<browsers.size();bId++) {
			System.out.println("Inside bId loop, bId="+bId);
			String sbrowser = (String) browsers.get(bId);
			System.out.println("sbrowser= "+sbrowser);
		for(int sId=0;sId<testsuites.size();sId++) {
			System.out.println("Inside sId loop, sId="+sId);
			JSONObject testsuite = (JSONObject) testsuites.get(sId);
			String runmode = (String) testsuite.get("runmode");
			if(runmode.equals("Y")) {
				String name = (String) testsuite.get("name");
				String testdatajsonfile =System.getProperty("user.dir")+"//src//test//resources//jsons//"+(String)testsuite.get("testdatajsonfile");
				String suitfilename = (String) testsuite.get("suitfilename");
				String paralleltests = (String) testsuite.get("paralleltests");
				System.out.println(name+"--------------"+runmode+"------------"+suitfilename);
				boolean pTests = false;
				if(paralleltests.equals("Y"))
					pTests = true;
				testNG.createSuite(name, pTests);
				
				testNG.addListener("listener.MyTestNGListener");
				
				String pathSuitJSON = System.getProperty("user.dir")+"//src//test//resources//jsons//"+suitfilename;
				JSONParser suitParser = new JSONParser();
				JSONObject suitJSON = (JSONObject) suitParser.parse(new FileReader(new File(pathSuitJSON)));
				//System.out.println(suitJSON);
				JSONArray suitTestCases = (JSONArray) suitJSON.get("testcases");
				//System.out.println(suitTestCases);
				for(int sTId=0;sTId<suitTestCases.size();sTId++) {
					System.out.println("#####inside sTId= "+sTId);
					JSONObject suitTestCase = (JSONObject) suitTestCases.get(sTId);
					String tName = (String) suitTestCase.get("name");
					JSONArray parameternames = (JSONArray) suitTestCase.get("parameternames");
					JSONArray executions = (JSONArray) suitTestCase.get("executions");
					for(int eId=0;eId<executions.size();eId++) {
						System.out.println("$$$$$$$inside eId= "+eId);
						JSONObject testCase = (JSONObject) executions.get(eId);
						String tRunMode = (String) testCase.get("runmode");
						if(tRunMode!=null && tRunMode.equals("Y")) {
							String executionname = (String) testCase.get("executionname");
							String dataflag = (String) testCase.get("dataflag");
							int dataSets = new DataUtil().getTestDataSets(testdatajsonfile,dataflag);
							// check how many sets of data are there
							for(int dSId=0;dSId<dataSets;dSId++) {
								System.out.println("^^^^^^^^^inside dSId= "+dSId);
								JSONArray parametervalues = (JSONArray) testCase.get("parametervalues");
								JSONArray methods = (JSONArray) testCase.get("methods");
								System.out.println(tName+"-"+executionname);
								System.out.println(parameternames+"-"+parametervalues);
								System.out.println(methods);
								// add to testNg
								testNG.addTest(tName+"-"+executionname+"-IT.-"+(dSId+1)); // dSId+1 so iteration 0 will be 1
								for(int pId=0;pId<parameternames.size();pId++) {
									testNG.addTestParameter((String)parameternames.get(pId),(String)parametervalues.get(pId));
								}
								testNG.addTestParameter("dataflag", dataflag);
								testNG.addTestParameter("datafilepath", testdatajsonfile);
								testNG.addTestParameter("iteration", String.valueOf(dSId));
								testNG.addTestParameter("sbrowser", sbrowser);
								List<String> includedMethods = new ArrayList<String>();
								
								for(int mId=0;mId<methods.size();mId++) {
									String method = (String) methods.get(mId);
									String methodClass = classMethods.get(method);
									//System.out.println(method+"------->"+methodClass);
									if(mId==methods.size()-1||!((String)classMethods.get((String)methods.get(mId+1))).equals(methodClass)) {
										//next method is from different class
										includedMethods.add(method);
										testNG.addTestClass(methodClass, includedMethods);
										includedMethods = new ArrayList<String>();
									}// end of if loop
									else
										//same class
										includedMethods.add(method);
								} // end of mId for loop
								
								System.out.println("***************************************-------------------------------------");
						 } // end of dSId for loop
					  } // end of if loop tRunMode!=null && tRunMode.equals("y")
					}// end of eId for loop
					
				}// end of sTId for loop
				System.out.println("Before testng.run() and after end of sTId loop");
			//	testNG.run();
				
			}// end of (runmode.equals("Y"))
		
		}// end of sId for loop
		}// end of bId for loop
		testNG.run();
	}

}
