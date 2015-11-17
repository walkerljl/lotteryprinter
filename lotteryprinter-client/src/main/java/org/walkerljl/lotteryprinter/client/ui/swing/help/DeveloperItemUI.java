package org.walkerljl.lotteryprinter.client.ui.swing.help;

import org.walkerljl.lotteryprinter.client.MainUI;
import org.walkerljl.lotteryprinter.client.common.MessageUtils;
import org.walkerljl.lotteryprinter.client.ui.swing.ItemAction;

/**
 * 开发者信息UI
 * 
 * @author lijunlin
 */
public class DeveloperItemUI implements ItemAction {
	/**
	 * 构造函数
	 * 
	 * @param mainUI
	 */
	public DeveloperItemUI(MainUI mainUI) {
	}

	@Override
	public void action() {
		// 显示开发者信息
		StringBuilder info = new StringBuilder();
		info.append("Developer: www.walkerljl.org\n").append("Mobile: 18682651280\n").append("Email: lijunlins@163.com\n").append("微信: walker_ljl\n").append("QQ: 1434487791\n");
		MessageUtils.info(info.toString());
	}

}
