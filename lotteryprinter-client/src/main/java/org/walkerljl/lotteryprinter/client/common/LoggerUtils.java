package org.walkerljl.lotteryprinter.client.common;

import java.awt.Color;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import org.walkerljl.commons.datetime.DateUtils;
import org.walkerljl.commons.log.Logger;
import org.walkerljl.commons.log.LoggerFactory;


/**
 * 
 * LoggerUtils
 *
 * @author lijunlin
 */
public class LoggerUtils {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LoggerUtils.class);
	
	/** 日志输出面板*/
	private static JTextPane textPane;
	private static LoggerUtils instance;

	/**
	 * 构造函数
	 * @param inputTextPane
	 */
	private LoggerUtils(JTextPane inputTextPane) {
		textPane = inputTextPane;
	}

	/**
	 * 创建实例
	 * @param textPane
	 * @return
	 */
	public static LoggerUtils createInstance(JTextPane textPane) {
		return new LoggerUtils(textPane);
	}

	/**
	 * 获取实例
	 * @return
	 */
	public static LoggerUtils getInstance() {
		if (instance == null) {
			instance = new LoggerUtils(textPane);
		}
		return instance;
	}

	/**
	 * 输出INFO日志
	 * @param message
	 */
	public void info(String message) {
		info(message, null);
	}
	
	/**
	 * 输出INFO日志
	 * @param message
	 * @param e
	 */
	public void info(String message, Throwable e) {
		synchronized (LoggerUtils.class) {
			this.process(message, "INFO", Color.black);
		}
		LOGGER.info(message, e);
	}

	/**
	 * 输出警告日志
	 * @param message
	 */
	public void alert(String message) {
		alert(message, null);
	}
	
	/**
	 * 输出警告日志
	 * @param message
	 * @param e
	 */
	public void alert(String message, Throwable e) {
		synchronized (LoggerUtils.class) {
			this.process(message, "ALERT", Color.black);
		}
		LOGGER.warn(message, e);
	}

	/**
	 * 输出错误日志
	 * @param message
	 */
	public void error(String message) {
		error(message, null);
	}
	
	/**
	 * 输出错误日志
	 * @param message
	 * @param e
	 */
	public void error(String message, Throwable e) {
		synchronized (LoggerUtils.class) {
			this.process(message, "ERROR", Color.red);
		}
		LOGGER.error(message, e);
	}

	/**
	 * 处理日志
	 * @param message
	 * @param type
	 * @param color
	 */
	private void process(String message, String type, Color color) {
		//设置显示字体属性
		Document doc = textPane.getDocument();
		SimpleAttributeSet attrSet = new SimpleAttributeSet();
		StyleConstants.setForeground(attrSet, color);
		if (type.equals("ALERT")) {
			StyleConstants.setBold(attrSet, true);
		} else {
			StyleConstants.setBold(attrSet, false);
			StyleConstants.setFontSize(attrSet, 14);
			StyleConstants.setFontFamily(attrSet, "Courier");
		}

		//处理显示信息
		StringBuilder result = new StringBuilder();
		result.append(DateUtils.getCurrentDateTime()).append("[");
		result.append(type).append("]: ").append(message).append("\n");

		try {
			//绘制
			doc.insertString(doc.getLength(), result.toString(), attrSet);
			textPane.setCaretPosition(doc.getLength());

		} catch (BadLocationException e) {
			LOGGER.error("绘制日志内容出错:" + e.getMessage(), e);
		}
	}
}