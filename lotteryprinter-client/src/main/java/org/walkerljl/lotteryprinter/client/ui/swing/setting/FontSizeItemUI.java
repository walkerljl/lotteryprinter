package org.walkerljl.lotteryprinter.client.ui.swing.setting;

import javax.swing.JOptionPane;

import org.walkerljl.lotteryprinter.client.MainUI;
import org.walkerljl.lotteryprinter.client.common.LogUtils;
import org.walkerljl.lotteryprinter.client.common.MessageUtils;
import org.walkerljl.lotteryprinter.client.common.PropertiesUtils;
import org.walkerljl.lotteryprinter.client.ui.swing.ItemAction;

/**
 * 设置字体大小UI
 * 
 * @author lijunlin
 */
public class FontSizeItemUI implements ItemAction {
	/** 文件路径 */
	private String filePath = "./config/font-size.properties";
	/** 字体大小 */
	private String fontSize;

	/**
	 * 构造函数
	 * 
	 * @param mainUI
	 */
	public FontSizeItemUI(MainUI mainUI) {
		// 加载配置文件中的数据到界面
		PropertiesUtils propertiesUtil = new PropertiesUtils(filePath);
		this.fontSize = propertiesUtil.getValue("fontSize");
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
			PropertiesUtils propertiesUtil = new PropertiesUtils();
			propertiesUtil.setValue("fontSize", result);
			propertiesUtil.store(filePath, "");

			MessageUtils.info("设置成功！");
		} catch (Exception ex) {
			LogUtils.getInstance().error("字体大小设置出错！");
		}
	}

}
