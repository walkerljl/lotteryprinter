package org.walkerljl.lotteryprinter.client.ui.swing.setting;

import java.util.Properties;

import javax.swing.JOptionPane;

import org.walkerljl.commons.util.PropertiesUtils;
import org.walkerljl.lotteryprinter.client.common.Constants;
import org.walkerljl.lotteryprinter.client.common.LoggerUtils;
import org.walkerljl.lotteryprinter.client.common.MessageUtils;
import org.walkerljl.lotteryprinter.client.ui.swing.ItemAction;
import org.walkerljl.lotteryprinter.client.ui.swing.MainUI;

/**
 * 设置字体大小UI
 * 
 * @author lijunlin
 */
public class FontSizeItemUI implements ItemAction {
	
	private static final String PROPERTT_KEY = "fontSize";
	/** 字体大小 */
	private String fontSize;

	/**
	 * 构造函数
	 * 
	 * @param mainUI
	 */
	public FontSizeItemUI(MainUI mainUI) {
		// 加载配置文件中的数据到界面
		Properties properties = PropertiesUtils.createFromInputStream(getClass().getResourceAsStream(Constants.CONF_PROPERTIES));
		this.fontSize = PropertiesUtils.getPropertyAsString(properties, PROPERTT_KEY);
	}

	@Override
	public void action() {
		String result = JOptionPane.showInputDialog("请输入打印字体大小[单位:px,类型:整数]",
				this.fontSize);

		// 操作为取消时
		if (result == null)
			return;

		try {
			Integer.parseInt(result);
		} catch (Exception ex) {
			MessageUtils.alert("设置失败，请确保输入的数据为整数！");
			return;
		}

		try {
			Properties properties = new Properties();
			properties.setProperty(PROPERTT_KEY, result);
			PropertiesUtils.writeToFile(properties, getClass().getResource(Constants.CONF_PROPERTIES).getFile());

			MessageUtils.info("设置成功！");
		} catch (Exception ex) {
			LoggerUtils.getInstance().error("字体大小设置出错！");
		}
	}

}
