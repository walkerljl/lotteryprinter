package org.walkerljl.lotteryprinter.client.ui.swing.reprint;

import java.util.HashSet;
import java.util.Set;

import javax.swing.JOptionPane;

import org.walkerljl.lotteryprinter.client.common.MessageUtils;
import org.walkerljl.lotteryprinter.client.ui.swing.ItemAction;
import org.walkerljl.lotteryprinter.client.ui.swing.MainUI;

/**
 * 
 * SingleHandleItemUI
 *
 * @author lijunlin
 */
public class SingleHandleItemUI implements ItemAction {
	/** 主窗体对象 */
	private MainUI mainUI;

	/**
	 * 构造函数
	 * 
	 * @param mainUI
	 */
	public SingleHandleItemUI(MainUI mainUI) {
		this.mainUI = mainUI;
	}

	@Override
	public void action() {
		String result = JOptionPane.showInputDialog("请输入需要打印的任务编号");

		if (result == null)
			return;

		if (result.length() != 10) {
			MessageUtils.alert("任务编号的长度必须为10位。");
			return;
		} else {
			try {
				Long.parseLong(result);
			} catch (Exception ex) {
				MessageUtils.alert("任务编号组成类型必须为数字，且第一位不能为零。");
				return;
			}
		}

		// 调用业务处理函数处理
		Set<Long> idSet = new HashSet<Long>();
		idSet.add(Long.parseLong(result));
		this.mainUI.getLotteryPrinterService().addTaskByIds(idSet);
	}

}
