package org.walkerljl.lotteryprinter.client.ui.swing.setting;

import javax.swing.JOptionPane;

import org.walkerljl.lotteryprinter.client.common.LogUtils;
import org.walkerljl.lotteryprinter.client.common.MessageUtils;
import org.walkerljl.lotteryprinter.client.common.PropertiesUtils;
import org.walkerljl.lotteryprinter.client.ui.swing.ItemAction;
import org.walkerljl.lotteryprinter.client.ui.swing.MainUI;

/**
 * 设置任务分配时间间隔UI
 * 
 * @author lijunlin
 */
public class TaskAssignIntervalItemUI implements ItemAction {
	/** 文件路径 */
	private String filePath = "./config/task-assign-interval.properties";
	/** 任务分配时间间隔 */
	private String taskAssignInterval;

	/**
	 * 构造函数
	 * 
	 * @param mainUI
	 */
	public TaskAssignIntervalItemUI(MainUI mainUI) {
		// 加载配置文件中的数据到界面
		PropertiesUtils propertiesUtil = new PropertiesUtils(filePath);
		this.taskAssignInterval = propertiesUtil.getValue("taskAssignInterval");
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
			PropertiesUtils propertiesUtil = new PropertiesUtils();
			propertiesUtil.setValue("taskAssignInterval", result);
			propertiesUtil.store(filePath, "");

			MessageUtils.info("设置成功！");
		} catch (Exception ex) {
			LogUtils.getInstance().error("任务分配时间间隔设置出错！");
		}
	}

}
