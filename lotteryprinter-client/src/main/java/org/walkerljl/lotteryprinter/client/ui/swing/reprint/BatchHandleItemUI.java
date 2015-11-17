package org.walkerljl.lotteryprinter.client.ui.swing.reprint;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.swing.JOptionPane;

import org.walkerljl.lotteryprinter.client.common.MessageUtils;
import org.walkerljl.lotteryprinter.client.ui.swing.ItemAction;
import org.walkerljl.lotteryprinter.client.ui.swing.MainUI;

/**
 * 
 * BatchHandleItemUI
 *
 * @author lijunlin
 */
public class BatchHandleItemUI implements ItemAction {
	/** 主窗体对象 */
	private MainUI mainUI;

	/**
	 * 构造函数
	 * 
	 * @param mainUI
	 */
	public BatchHandleItemUI(MainUI mainUI) {
		this.mainUI = mainUI;
	}

	@Override
	public void action() {
		String result = JOptionPane.showInputDialog("请输入需要打印的任务编号区间[中间用'-'隔开]");
		if (result == null)
			return;

		// 判断输入的是否为任务编号区间
		if (result.indexOf('-') == -1) {// 输入的为单个任务编号

			// 输入验证
			if (!verify(result))
				return;
			// 处理单个情况
			handle(result);
		} else {// 输入的为任务编号区间
			int index = result.indexOf('-');
			String min = result.substring(0, index);
			// System.out.println("min= " +min);
			if (!verify(min))
				return;
			String max = result.substring(index + 1);
			// System.out.println("max= " +max);
			if (!verify(max))
				return;

			// 处理
			handle(min, max);
		}

	}

	/**
	 * 单个编号合法性验证
	 * 
	 * @param result
	 * @return
	 */
	private boolean verify(String result) {
		if (result.length() != 10) {
			MessageUtils.alert("任务编号的长度必须为10位。");
			return false;
		} else {
			try {
				Long.parseLong(result);
			} catch (Exception ex) {
				MessageUtils.alert("任务编号组成类型必须为数字，且第一位不能为零。");
				return false;
			}
		}

		return true;
	}

	private void handle(String min, String max) {
		long start = Long.parseLong(min);
		long end = Long.parseLong(max);

		// 调用业务处理函数处理
		Set<Long> idSet = new LinkedHashSet<Long>();
		while (start <= end) {
			idSet.add(new Long(start));
			start++;
		}
		this.mainUI.getLotteryPrinterService().addTaskByIds(idSet);
	}

	private void handle(String result) {
		// 调用业务处理函数处理
		Set<Long> idSet = new HashSet<Long>();
		idSet.add(Long.parseLong(result));
		this.mainUI.getLotteryPrinterService().addTaskByIds(idSet);
	}

}
