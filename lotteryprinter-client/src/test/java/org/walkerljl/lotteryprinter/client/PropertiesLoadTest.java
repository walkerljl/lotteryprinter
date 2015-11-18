package org.walkerljl.lotteryprinter.client;

import java.util.Properties;

import org.walkerljl.commons.util.PropertiesUtils;

/**
 *
 * PropertiesLoadTest
 *
 * @author lijunlin
 */
public class PropertiesLoadTest {

	public static void main(String[] args) {
		try {
			Properties properties = PropertiesUtils.createFromInputStream(PropertiesLoadTest.class.getResourceAsStream("/coord.properties"));
			System.out.println("topVersionX=" + PropertiesUtils.getPropertyAsString(properties, "topVersionX"));
			properties.setProperty("xxx", "xxxxxxx");
			PropertiesLoadTest.class.getClassLoader().getResource("").getFile();
			PropertiesUtils.writeToFile(properties, "/coord.properties");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
