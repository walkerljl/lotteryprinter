package org.walkerljl.lotteryprinter.client.ui.swing.run;

import java.util.Map;

import org.walkerljl.lotteryprinter.client.common.LoggerUtils;
import org.walkerljl.lotteryprinter.client.common.MessageUtils;
import org.walkerljl.lotteryprinter.client.ui.swing.ItemAction;
import org.walkerljl.lotteryprinter.client.ui.swing.MainUI;

/**
 * 
 * RunItemUI
 *
 * @author lijunlin
 */
public class RunItemUI implements ItemAction {
	/** 主窗体对象 */
	private MainUI mainUI;

	/**
	 * 构造函数
	 * 
	 * @param mainUI
	 */
	public RunItemUI(MainUI mainUI) {
		this.mainUI = mainUI;
	}

	@Override
	public void action() {
		if (this.mainUI.isPause()) {
			executePrintAction();
		} else {
			pausePrintAction();
		}
	}

	/**
	 * 执行打印按钮事件
	 */
	private void executePrintAction() {
		if (!this.mainUI.getLotteryPrinterService().isRestOfTask()) {
			MessageUtils.alert("您的任务队列为空，请选择需要打印的数据！");
		} else if (this.mainUI.getSelectedPrintServices().size() <= 0) {
			MessageUtils.alert("请选择至少一项打印服务!");
		} else {
			StringBuilder info = new StringBuilder();
			info.append("...........................\n");
			info.append("以下打印服务正在为您打印：\n");
			for (Map.Entry<String, Object> entry : this.mainUI
					.getSelectedPrintServices().entrySet())
				info.append(entry.getKey()).append("\n");
			info.append("..........请耐心等待..........");
			LoggerUtils.getInstance().info(info.toString());

			this.mainUI.setPause(false);
			this.mainUI.getLotteryPrinterService().startConsumeTask(
					this.mainUI.getSelectedPrintServices());
			this.mainUI.getRunItem().setText("暂停");
		}
	}

	/**
	 * 暂停打印按钮事件
	 */
	private void pausePrintAction() {
		if (!this.mainUI.isPause()) {
			this.mainUI.setPause(true);
			this.mainUI.getLotteryPrinterService().pause();
			this.mainUI.getRunItem().setText("继续");
		}
	}

}
