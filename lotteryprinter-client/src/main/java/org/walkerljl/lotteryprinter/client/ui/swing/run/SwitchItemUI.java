package org.walkerljl.lotteryprinter.client.ui.swing.run;

import org.walkerljl.lotteryprinter.client.common.MessageUtils;
import org.walkerljl.lotteryprinter.client.ui.swing.ItemAction;
import org.walkerljl.lotteryprinter.client.ui.swing.MainUI;

/**
 * 
 * SwitchItemUI
 *
 * @author lijunlin
 */
public class SwitchItemUI implements ItemAction {
	/** 主窗体对象 */
	private MainUI mainUI;

	/**
	 * 构造函数
	 * 
	 * @param mainUI
	 */
	public SwitchItemUI(MainUI mainUI) {
		this.mainUI = mainUI;
	}

	@Override
	public void action() {
		doSwitchAction();
	}

	/**
	 * 切换按钮事件
	 */
	private void doSwitchAction() {
		if (!this.mainUI.getLotteryPrinterService().isCanSwitch()) {
			MessageUtils.alert("您暂时不能进行切换操作！");
		} else {
			this.mainUI.getLotteryPrinterService().doSwitch();
			// 设置为暂停
			this.mainUI.setPause(true);
			this.mainUI.getRunItem().setText("打印");
		}
	}

}
