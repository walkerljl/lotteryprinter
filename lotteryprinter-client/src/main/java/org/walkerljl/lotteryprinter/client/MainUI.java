package org.walkerljl.lotteryprinter.client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import org.walkerljl.lotteryprinter.client.common.LogUtils;
import org.walkerljl.lotteryprinter.client.common.MessageUtils;
import org.walkerljl.lotteryprinter.client.common.SystemProperties;
import org.walkerljl.lotteryprinter.client.service.LotteryPrinterService;
import org.walkerljl.lotteryprinter.client.service.impl.LotteryPrinterServiceImpl;
import org.walkerljl.lotteryprinter.client.ui.swing.ItemAction;

/**
 * MainUI
 *
 * @author lijunlin
 */
public class MainUI extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	/*
	 * 主窗体元素
	 */
	/** 菜单栏 */
	private JMenuBar menuBar = new JMenuBar();
	/** 菜单_文件 */
	private JMenu menuFile = new JMenu("文件");
	private JMenuItem fileImportItem = new JMenuItem("导入");
	private JMenuItem exitItem = new JMenuItem("退出");
	/** 菜单_预览 */
	// private JMenu menuPreview = new JMenu("预览");
	// private JMenuItem previewItem = new JMenuItem("预览");
	/** 菜单_服务 */
	private JMenu menuService = new JMenu("服务");
	private JMenuItem choiceServiceItem = new JMenuItem("选择");
	/** 菜单_打印 */
	private JMenu menuPrint = new JMenu("打印");
	private JMenuItem produceTaskItem = new JMenuItem("生成任务");
	private JMenuItem runItem = new JMenuItem("运行");
	private JMenuItem switchItem = new JMenuItem("切换");
	/** 菜单_重打 */
	private JMenu menuReprint = new JMenu("重打");
	private JMenuItem singleHandleItem = new JMenuItem("单个录入");
	private JMenuItem batchHandleItem = new JMenuItem("批量处理");
	private JMenuItem excelHandleItem = new JMenuItem("Excel");
	/** 菜单_设置 */
	private JMenu menuSetting = new JMenu("设置");
	private JMenuItem coordItem = new JMenuItem("打印坐标");
	private JMenuItem fontSizeItem = new JMenuItem("字体大小");
	private JMenuItem taskAssignIntervalItem = new JMenuItem("任务分配周期");
	/** 菜单_帮助 */
	private JMenu menuHelp = new JMenu("帮助");
	// private JMenuItem userManualItem = new JMenuItem("用户手册");
	private JMenuItem versionsItem = new JMenuItem("程序版本");
	private JMenuItem authorItem = new JMenuItem("开发者信息");

	/** 信息显示面板 */
	private JTextPane textPane = new JTextPane();
	/** 滚动面板 */
	private JScrollPane scrollPane = new JScrollPane(textPane);
	/** 按钮事件、响应方法映射 */
	private Map<String, String> itemActionMap = new HashMap<String, String>();

	/** 日志 */
	private LogUtils logger = LogUtils.createInstance(textPane);
	/** 打印业务接口 */
	private LotteryPrinterService lotteryPrinterService = new LotteryPrinterServiceImpl();;

	/** Excel文件路径 */
	private String filePath;
	/** 记录添加到任务队列当中的文件名 */
	private Map<String, Object> fileNameMap = new HashMap<String, Object>();
	/** 选中的打印服务 */
	private Map<String, Object> selectedPrintServices = new HashMap<String, Object>();
	/** 继续打印、暂停打印切换标志 */
	private boolean isPause = true;

	/**
	 * 构造函数
	 */
	public MainUI() {
		// 初始界面布局、注册监听
		initLayout();

		itemActionMap.put("fileImportItem", "file.FileImportItemUI");
		// itemActionMap.put("previewItem", "preview.PreviewItemUI");
		itemActionMap.put("choiceServiceItem", "service.ChoiceServiceItemUI");
		itemActionMap.put("produceTaskItem", "run.ProduceTaskItemUI");
		itemActionMap.put("runItem", "run.RunItemUI");
		itemActionMap.put("switchItem", "run.SwitchItemUI");
		itemActionMap.put("singleHandleItem", "reprint.SingleHandleItemUI");
		itemActionMap.put("batchHandleItem", "reprint.BatchHandleItemUI");
		itemActionMap.put("excelHandleItem", "reprint.ExcelHandleItemUI");
		itemActionMap.put("coordItem", "setting.CoordItemUI");
		itemActionMap.put("fontSizeItem", "setting.FontSizeItemUI");
		itemActionMap.put("taskAssignIntervalItem",
				"setting.TaskAssignIntervalItemUI");
		itemActionMap.put("versionsItem", "help.VersionItemUI");
		itemActionMap.put("authorItem", "help.DeveloperItemUI");
	}

	/**
	 * 创建元素初始化、设置监听
	 */
	private void initMenuBar() {
		/** 文件菜单 */
		// 文件导入菜单项
		this.fileImportItem.setName("fileImportItem");
		this.fileImportItem.addActionListener(this);
		this.menuFile.add(fileImportItem);
		// 注销菜单项
		this.exitItem.setName("exitName");
		this.exitItem.addActionListener(this);
		this.menuFile.add(exitItem);

		this.menuBar.add(menuFile);

		/** 预览菜单 */
		// 预览菜单项
		// this.previewItem.setName("previewItem");
		// this.previewItem.addActionListener(this);
		// this.menuPreview.add(previewItem);
		// this.menuBar.add(menuPreview);

		/** 服务菜单 */
		// 选择服务菜单项
		this.choiceServiceItem.setName("choiceServiceItem");
		this.choiceServiceItem.addActionListener(this);
		this.menuService.add(choiceServiceItem);

		this.menuBar.add(menuService);

		/** 运行菜单 */
		// 生产任务菜单项
		this.produceTaskItem.setName("produceTaskItem");
		this.produceTaskItem.addActionListener(this);
		this.menuPrint.add(produceTaskItem);
		// 运行菜单项
		this.runItem.setName("runItem");
		this.runItem.addActionListener(this);
		this.menuPrint.add(runItem);
		// 切换菜单项
		this.switchItem.setName("switchItem");
		this.switchItem.addActionListener(this);
		this.menuPrint.add(switchItem);

		this.menuBar.add(menuPrint);

		/** 重打菜单 */
		// 单个录入菜单项
		this.singleHandleItem.setName("singleHandleItem");
		this.singleHandleItem.addActionListener(this);
		this.menuReprint.add(singleHandleItem);
		// 批量处理菜单项
		this.batchHandleItem.setName("batchHandleItem");
		this.batchHandleItem.addActionListener(this);
		this.menuReprint.add(batchHandleItem);
		// Excel菜单项
		this.excelHandleItem.setName("excelHandleItem");
		this.excelHandleItem.addActionListener(this);
		this.menuReprint.add(excelHandleItem);

		this.menuBar.add(menuReprint);

		/** 设置菜单 */
		// 坐标设置菜单项
		this.coordItem.setName("coordItem");
		this.coordItem.addActionListener(this);
		this.menuSetting.add(coordItem);
		// 字体大小设置菜单项
		this.fontSizeItem.setName("fontSizeItem");
		this.fontSizeItem.addActionListener(this);
		this.menuSetting.add(fontSizeItem);
		// 任务分配间隔设置菜单项
		this.taskAssignIntervalItem.setName("taskAssignIntervalItem");
		this.taskAssignIntervalItem.addActionListener(this);
		this.menuSetting.add(taskAssignIntervalItem);

		this.menuBar.add(menuSetting);

		/** 帮助菜单 */
		// 用户手册菜单项
		// this.userManualItem.setName("userManualItem");
		// this.userManualItem.addActionListener(this);
		// this.menuHelp.add(userManualItem);
		// 产品版本信息菜单项
		this.versionsItem.setName("versionsItem");
		this.versionsItem.addActionListener(this);
		this.menuHelp.add(versionsItem);
		// 作者信息菜单项
		this.authorItem.setName("authorItem");
		this.authorItem.addActionListener(this);
		this.menuHelp.add(authorItem);

		this.menuBar.add(menuHelp);
	}

	/**
	 * 初始界面布局、注册监听
	 */
	private void initLayout() {
		// 设置应用程序名称
		this.setTitle("彩票打印");
		// 使用 System exit 方法退出应用程序。仅在应用程序中使用。
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// this.setBounds(0, 0, (int)SystemProperties.SCREEN_WIDTH,
		// (int)SystemProperties.SCREEN_HEIGHT);
		this.setBounds((int) ((SystemProperties.SCREEN_WIDTH - 800) / 2),
				(int) ((SystemProperties.SCREEN_HEIGHT - 600) / 2), 800, 600);

		// 初始化菜单工具栏
		initMenuBar();
		this.setJMenuBar(this.menuBar);

		// 初始化信息显示面板
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(this.scrollPane, BorderLayout.CENTER);

		this.setResizable(false);
		this.validate();
		this.setVisible(true);
	}

	/**
	 * 按钮事件响应函数
	 * 
	 * @param clazzName
	 */
	private void actions(String clazzName) {
		try {
			Class<?> clazz = Class.forName("org.walkerljl.lotteryprinter.client.ui.swing."
					+ clazzName);
			Constructor<?> constructor = clazz.getConstructor(MainUI.class);
			ItemAction itemAction = (ItemAction) constructor.newInstance(this);
			// 通过反射调用类里面指定的方法
			itemAction.action();

		} catch (Exception ex) {
			logger.error("系统无法响应您的操作！");
			ex.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JMenuItem src = (JMenuItem) e.getSource();

		if (src == exitItem)
			exitAppAction();
		else if (itemActionMap.containsKey(src.getName()))
			actions(itemActionMap.get(src.getName()));
	}

	/**
	 * 退出系统按钮事件
	 */
	private void exitAppAction() {
		if (lotteryPrinterService.isRestOfTask()) {
			int result = MessageUtils.confirm("你还有多份任务未打印，确认要退出吗？");
			// 确认退出应用
			if (result == JOptionPane.YES_OPTION) {
				destroyApp();
			}
		} else {
			int result = MessageUtils.confirm("确认要退出吗？");
			// 确认退出应用
			if (result == JOptionPane.YES_OPTION) {
				destroyApp();
			}
		}

	}

	/**
	 * 释放应用资源
	 */
	private void destroyApp() {
		this.setVisible(false);
		this.dispose();
		System.exit(0);
	}

	/**
	 * 应用入口
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		new MainUI();
	}

	/**
	 * 点击右上角的叉叉无法关闭程序
	 */
	@Override
	protected void processWindowEvent(WindowEvent e) {
		if (e.getID() != WindowEvent.WINDOW_CLOSING) {
			super.processWindowEvent(e);
		}
	}

	public LotteryPrinterService getLotteryPrinterService() {
		return lotteryPrinterService;
	}

	public LogUtils getLogger() {
		return logger;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public Map<String, Object> getSelectedPrintServices() {
		return selectedPrintServices;
	}

	public void setSelectedPrintServices(
			Map<String, Object> selectedPrintServices) {
		this.selectedPrintServices = selectedPrintServices;
	}

	public Map<String, Object> getFileNameMap() {
		return fileNameMap;
	}

	public JMenuItem getSwitchItem() {
		return switchItem;
	}

	public void setSwitchItem(JMenuItem switchItem) {
		this.switchItem = switchItem;
	}

	public boolean isPause() {
		return isPause;
	}

	public void setPause(boolean isPause) {
		this.isPause = isPause;
	}

	public JMenuItem getRunItem() {
		return runItem;
	}
}