package org.walkerljl.lotteryprinter.client.common;

import java.io.File;

import org.walkerljl.log.Logger;
import org.walkerljl.log.LoggerFactory;

/**
 *
 * 配置
 *
 * @author lijunlin
 */
public class Constants {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Constants.class);
	
	private static String getBaseFilePath() {
		String baseFilePath = System.getProperty("user.dir") + File.separator + "conf";
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Current baseFilePath:" + baseFilePath);
		}
		return baseFilePath;
	}
	
	public static String getPrintCoordFilePath() {
		String path = getBaseFilePath() + File.separator + "coord.properties";
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Current printCoordFilePath:" + path);
		}
		return path;
	}
	
	public static String getConfFilePath() {
		String path = getBaseFilePath() + File.separator + "lotteryprinter.properties";
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Current confFilePath:" + path);
		}
		return path;
	}
}