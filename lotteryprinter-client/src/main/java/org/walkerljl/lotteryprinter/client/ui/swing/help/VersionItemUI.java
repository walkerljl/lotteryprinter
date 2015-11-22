package org.walkerljl.lotteryprinter.client.ui.swing.help;

import java.util.Properties;

import org.walkerljl.commons.util.PropertiesUtils;
import org.walkerljl.lotteryprinter.client.common.Constants;
import org.walkerljl.lotteryprinter.client.common.MessageUtils;
import org.walkerljl.lotteryprinter.client.ui.swing.ItemAction;
import org.walkerljl.lotteryprinter.client.ui.swing.MainUI;

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
		Properties properties = PropertiesUtils.createFromInputStream(getClass().getResourceAsStream(Constants.CONF_PROPERTIES));
		info.append("当前程序版本:  ").append(PropertiesUtils.getPropertyAsString(properties, "version"));
		MessageUtils.info(info.toString());
	}
}