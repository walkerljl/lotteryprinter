package org.walkerljl.lotteryprinter.client.ui.swing.help;

import org.walkerljl.lotteryprinter.client.MainUI;
import org.walkerljl.lotteryprinter.client.common.MessageUtils;
import org.walkerljl.lotteryprinter.client.ui.swing.ItemAction;

/**
 * 程序版本信息UI
 * 
 * @author lijunlin
 */
public class VersionItemUI implements ItemAction {
	/**
	 * 构造函数
	 * 
	 * @param mainUI
	 */
	public VersionItemUI(MainUI mainUI) {
	}

	@Override
	public void action() {
		// 显示开发者信息
		StringBuilder info = new StringBuilder();
		info.append("当前程序版本:  3.0.1");
		MessageUtils.info(info.toString());
	}
}