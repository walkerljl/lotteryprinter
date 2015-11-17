package org.walkerljl.lotteryprinter.client.ui.swing.service;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Map;

import javax.print.PrintService;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.walkerljl.lotteryprinter.client.common.PrintUtils;
import org.walkerljl.lotteryprinter.client.common.SystemProperties;
import org.walkerljl.lotteryprinter.client.ui.swing.ItemAction;
import org.walkerljl.lotteryprinter.client.ui.swing.MainUI;

/**
 * 
 * ChoiceServiceItemUI
 *
 * @author lijunlin
 */
public class ChoiceServiceItemUI extends JDialog implements ItemAction,
		ActionListener, ItemListener {
	private static final long serialVersionUID = 1L;

	private JButton okButton = new JButton("确认");
	private JButton cancelButton = new JButton("取消");
	private JPanel panel = new JPanel();
	private JPanel buttonPanel = new JPanel();
	/** 主窗体 */
	private MainUI mainUI;
	/** 选中的打印服务 */
	private Map<String, Object> selectedPrintServices;

	/**
	 * 构造函数
	 * 
	 * @param mainUI
	 */
	public ChoiceServiceItemUI(MainUI mainUI) {
		super(mainUI, "选择打印服务", true);
		this.mainUI = mainUI;
		this.selectedPrintServices = this.mainUI.getSelectedPrintServices();
		setPrintServicesList();
		initLayout();
		this.setBounds((int) ((SystemProperties.SCREEN_WIDTH - 400) / 2),
				(int) ((SystemProperties.SCREEN_HEIGHT - 400) / 2), 400, 400);
	}

	/**
	 * 初始化UI布局并且注册监听器
	 */
	private void initLayout() {
		this.getContentPane().setLayout(new BorderLayout());
		panel.setLayout(new GridLayout(20, 1));
		this.getContentPane().add(new JScrollPane(panel), BorderLayout.CENTER);
		okButton.addActionListener(this);
		buttonPanel.add(okButton);
		cancelButton.addActionListener(this);
		buttonPanel.add(cancelButton);
		this.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
	}

	@Override
	public void action() {
		this.validate();
		this.setVisible(true);
	}

	/**
	 * 设置打印服务列表
	 */
	private void setPrintServicesList() {
		PrintService[] printServices = new PrintUtils().getAllPrintService();
		JCheckBox checkBox;
		for (PrintService printService : printServices) {
			boolean isSelected = false;
			// 勾选已经选中的打印服务
			if (selectedPrintServices.containsKey(printService.getName())) {
				isSelected = true;
			}
			checkBox = new JCheckBox(printService.getName(), isSelected);
			// 复选框注册监听
			checkBox.addItemListener(this);
			// 添加到JPanel容器中
			panel.add(checkBox);
		}
	}

	/**
	 * 按钮点击事件
	 */
	@Override
	public void actionPerformed(ActionEvent evt) {
		Object src = evt.getSource();

		if (src == okButton)
			getSelectedPrintServices();
		else if (src == cancelButton)
			cancel();
	}

	/**
	 * 复合框选取状态改变事件
	 */
	@Override
	public void itemStateChanged(ItemEvent e) {
		JCheckBox item = (JCheckBox) e.getItem();
		String printService = item.getText();

		if (e.getStateChange() == ItemEvent.SELECTED) {
			if (!selectedPrintServices.containsKey(printService))
				selectedPrintServices.put(printService, null);
		} else if (e.getStateChange() == ItemEvent.DESELECTED) {
			if (selectedPrintServices.containsKey(printService))
				selectedPrintServices.remove(printService);
		}
	}

	/**
	 * 获取选中的打印服务
	 */
	private void getSelectedPrintServices() {
		if (this.selectedPrintServices.size() <= 0) {
			JOptionPane.showConfirmDialog(null, "请选择至少一项打印服务！", "提示",
					JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
		} else {
			// 将选中的打印服务对象信息回传给主程序
			this.mainUI.setSelectedPrintServices(this.selectedPrintServices);
			// 关闭选取打印服务的对话框
			this.setVisible(false);
			this.dispose();
		}
	}

	/**
	 * 取消按钮事件
	 */
	private void cancel() {
		this.setVisible(false);
		this.dispose();
	}

}
