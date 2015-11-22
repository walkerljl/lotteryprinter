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
 * 设置任务分配时间间隔UI
 * 
 * @author lijunlin
 */
public class TaskAssignIntervalItemUI implements ItemAction {

	private static final String PROPERTT_KEY = "taskAssignInterval";
	/** 任务分配时间间隔 */
	private String taskAssignInterval;

	/**
	 * 构造函数
	 * 
	 * @param mainUI
	 */
	public TaskAssignIntervalItemUI(MainUI mainUI) {
		// 加载配置文件中的数据到界面
		Properties properties = PropertiesUtils.createFromInputStream(getClass().getResourceAsStream(Constants.CONF_PROPERTIES));
		this.taskAssignInterval = PropertiesUtils.getPropertyAsString(properties, PROPERTT_KEY);
	}

	@Override
	public void action() {
		String result = JOptionPane.showInputDialog("请输入任务分配时间间隔[单位：s,类型:整数]",
				this.taskAssignInterval);

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
			LoggerUtils.getInstance().error("任务分配时间间隔设置出错！", ex);
		}
	}

}
