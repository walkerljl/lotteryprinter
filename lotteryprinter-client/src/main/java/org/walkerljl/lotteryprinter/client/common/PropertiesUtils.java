package org.walkerljl.lotteryprinter.client.common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 * 配置文件操作工具
 * 
 * @author lijunlin
 */
public class PropertiesUtils {
	/** Properties对象 */
	private Properties properties;
	/** 文件输入流 */
	private InputStream inputStream;
	/** 文件输出流 */
	private OutputStream outputStream;

	/**
	 * 构造函数
	 */
	public PropertiesUtils() {
		properties = new Properties();
	}

	/**
	 * 构造函数
	 * 
	 * @param filePath
	 */
	public PropertiesUtils(String filePath) {
		properties = new Properties();

		try {
			inputStream = new FileInputStream(filePath);
			properties.load(inputStream);
			inputStream.close();
		} catch (FileNotFoundException ex) {
			throw new RuntimeException("属性文件路径错误或者文件不存在！", ex);
		} catch (IOException ex) {
			throw new RuntimeException("加载文件失败！", ex);
		} finally {
			if (inputStream == null)
				return;
			try {
				inputStream.close();
			} catch (Exception ex) {
				throw new RuntimeException("关闭文件输入流失败！", ex);
			}
		}

	}

	/**
	 * 获取属性值
	 * 
	 * @param key
	 * @return
	 */
	public String getValue(String key) {
		String result = "";

		if (properties.containsKey(key))
			result = properties.getProperty(key);

		return result;
	}

	/**
	 * 设置属性值
	 * 
	 * @param key
	 * @param value
	 */
	public void setValue(String key, String value) {
		properties.setProperty(key, value);
	}

	/**
	 * 清除配置文件中的值
	 */
	public void clear() {
		properties.clear();
	}

	/**
	 * 将属性值保存到配置文件
	 * 
	 * @param filePath
	 * @param description
	 */
	public void store(String filePath, String description) {
		try {
			outputStream = new FileOutputStream(filePath);
			properties.store(outputStream, description);
			outputStream.close();
		} catch (FileNotFoundException ex) {
			throw new RuntimeException("属性文件路径错误或者文件不存在！", ex);
		} catch (IOException ex) {
			throw new RuntimeException("写入文件失败！", ex);
		} finally {
			if (outputStream == null)
				return;
			try {
				outputStream.close();
			} catch (Exception ex) {
				throw new RuntimeException("关闭文件输入流失败！", ex);
			}
		}

	}

}
