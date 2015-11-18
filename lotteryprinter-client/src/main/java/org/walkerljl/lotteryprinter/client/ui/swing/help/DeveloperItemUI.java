package org.walkerljl.lotteryprinter.client.ui.swing.help;

import java.util.Properties;

import org.walkerljl.commons.util.PropertiesUtils;
import org.walkerljl.lotteryprinter.client.common.Constants;
import org.walkerljl.lotteryprinter.client.common.MessageUtils;
import org.walkerljl.lotteryprinter.client.ui.swing.ItemAction;
import org.walkerljl.lotteryprinter.client.ui.swing.MainUI;

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
		Properties properties = PropertiesUtils.createFromInputStream(getClass().getResourceAsStream(Constants.CONF_PROPERTIES));
		// 显示开发者信息
		StringBuilder info = new StringBuilder();
		info.append("技术支持: ").append(PropertiesUtils.getPropertyAsString(properties, "company")).append("\n");
		info.append("电话: ").append(PropertiesUtils.getPropertyAsString(properties, "mobile")).append("\n");
		info.append("邮箱: ").append(PropertiesUtils.getPropertyAsString(properties, "email")).append("\n");
		info.append("微信: ").append(PropertiesUtils.getPropertyAsString(properties, "weixin")).append("\n");
		info.append("QQ: ").append(PropertiesUtils.getPropertyAsString(properties, "qq")).append("\n");
		MessageUtils.info(info.toString());
	}

}
