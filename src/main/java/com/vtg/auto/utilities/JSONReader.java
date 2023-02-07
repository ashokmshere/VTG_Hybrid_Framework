package com.vtg.auto.utilities;

import java.io.FileReader;
import java.io.Reader;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.vtg.auto.reusablecomponents.GlobalVariables;

public class JSONReader extends GlobalVariables {

	public String get(String testDataName) throws Exception {
		try{
			
		
			String namespace = Thread.currentThread().getStackTrace()[2].getClassName();
			int lastOccurence = namespace.lastIndexOf('.'); 
			String className =   namespace.toString().substring(namespace.toString().lastIndexOf("."));
			className= className.replace(".", "");
			String packageName =   namespace.toString().substring(namespace.toString().lastIndexOf(".", lastOccurence -1));
			packageName= packageName.replace(".", "");
			String methodName =   Thread.currentThread().getStackTrace()[2].getMethodName();
			methodName= methodName.replace(".", "");
			//packageName = packageName.replace(methodName, ""); //Commented on 10-Oct-2022 
			packageName = packageName.replace(className, "");

			Gson gson = new Gson();
			Reader reader = new FileReader(GlobalVariables.workingDir + "/src/test/resources/testdata/TestData.json");
			JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
			JsonArray testCaseArray = jsonObject.getAsJsonArray(packageName);
			for (JsonElement testCase : testCaseArray) {
				if (testCase.toString().contains(methodName)) {
					String testData = testCase.getAsJsonObject().getAsJsonObject(className).get(methodName)
							.getAsJsonObject().get(testDataName).getAsString();
					return testData;
				}

			}
		}
		catch (Exception e) {
			e.printStackTrace();
			 throw new Exception("Test Data Not Found in JSON: " + e); 
		}
		//exception
		return "Test Data : " + testDataName + " Not Found!";

	}
}
