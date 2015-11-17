package org.walkerljl.lotteryprinter.client.common;

import java.awt.Color;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * LogUtils
 *
 * @author lijunlin
 */
public class LogUtils {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LogUtils.class);
	
	private static JTextPane textPane;

	private static LogUtils instance;

	private LogUtils(JTextPane inputTextPane) {
		textPane = inputTextPane;
	}

	public static LogUtils createInstance(JTextPane textPane) {
		return new LogUtils(textPane);
	}

	public static synchronized LogUtils getInstance() {
		if (instance == null)
			instance = new LogUtils(textPane);

		return instance;
	}

	public synchronized void info(String message) {
		this.process(message, "INFO", Color.black);
	}

	public synchronized void alert(String message) {
		this.process(message, "ALERT", Color.black);
	}

	public synchronized void error(String message) {
		this.process(message, "ERROR", Color.red);
	}

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
		result.append(DateUtils.getAccurateTime()).append("[");
		result.append(type).append("]: ").append(message).append("\n");

		try {
			//绘制
			doc.insertString(doc.getLength(), result.toString(), attrSet);
			textPane.setCaretPosition(doc.getLength());

		} catch (BadLocationException e) {
			LOGGER.error("绘制日志内容出错:", e.getMessage(), e);
		}
	}
}