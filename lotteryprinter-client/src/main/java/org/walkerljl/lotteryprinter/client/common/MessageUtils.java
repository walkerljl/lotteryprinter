package org.walkerljl.lotteryprinter.client.common;

import javax.swing.JOptionPane;

/**
 * 消息工具
 * 
 * @author lijunlin
 */
public class MessageUtils {
	
	/**
	 * 警告信息
	 * 
	 * @param title
	 * @param content
	 */
	public static void alert(String content) {
		JOptionPane.showMessageDialog(null, content, "警告",
				JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * INFO
	 * @param content
	 */
	public static void info(String content) {
		JOptionPane.showMessageDialog(null, content, "消息",
				JOptionPane.PLAIN_MESSAGE);
	}

	/**
	 * 确认
	 * @param content
	 * @return
	 */
	public static int confirm(String content) {
		return JOptionPane.showConfirmDialog(null, content, "提示",
				JOptionPane.YES_NO_CANCEL_OPTION);
	}
}