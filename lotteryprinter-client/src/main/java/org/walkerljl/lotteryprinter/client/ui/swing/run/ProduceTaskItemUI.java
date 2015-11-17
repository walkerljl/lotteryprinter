package org.walkerljl.lotteryprinter.client.ui.swing.run;

import org.walkerljl.lotteryprinter.client.ui.swing.ItemAction;
import org.walkerljl.lotteryprinter.client.ui.swing.MainUI;

/**
 * 
 * ProduceTaskItemUI
 *
 * @author lijunlin
 */
public class ProduceTaskItemUI implements ItemAction {

	/** 主窗体对象 */
	private MainUI mainUI;

	/**
	 * 构造函数
	 * 
	 * @param mainUI
	 */
	public ProduceTaskItemUI(MainUI mainUI) {
		this.mainUI = mainUI;
	}

	@Override
	public void action() {
		this.mainUI.getLotteryPrinterService().startProduceTask();
	}

}