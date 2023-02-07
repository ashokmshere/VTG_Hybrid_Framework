package com.vtg.auto.utilities;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;


public class LoadProperties {

	public static Properties getProperties(String strPropertyFileName) {
		try {
			InputStream objFileReader = new FileInputStream(strPropertyFileName);
			Properties objProperties = new Properties();
			objProperties.load(objFileReader);
			return objProperties;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
