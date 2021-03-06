package org.walkerljl.lotteryprinter.client.ui.swing.setting;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.walkerljl.commons.util.PropertiesUtils;
import org.walkerljl.lotteryprinter.client.common.Constants;
import org.walkerljl.lotteryprinter.client.common.MessageUtils;
import org.walkerljl.lotteryprinter.client.common.SystemProperties;
import org.walkerljl.lotteryprinter.client.ui.swing.ItemAction;
import org.walkerljl.lotteryprinter.client.ui.swing.MainUI;

public class CoordItemUI extends JFrame implements ItemAction, ActionListener {
	private static final long serialVersionUID = 1L;

	/** 内容面板元素 */
	private VersionCoordUI topVersion = new VersionCoordUI("顶部版本号");

	private LotteryCoordUI lottery1 = new LotteryCoordUI("第一张彩票");
	private LotteryCoordUI lottery2 = new LotteryCoordUI("第二张彩票");
	private LotteryCoordUI lottery3 = new LotteryCoordUI("第三张彩票");
	private LotteryCoordUI lottery4 = new LotteryCoordUI("第四张彩票");
	private LotteryCoordUI lottery5 = new LotteryCoordUI("第五张彩票");
	private LotteryCoordUI lottery6 = new LotteryCoordUI("第六张彩票");

	private LotteryCoordUI lottery7 = new LotteryCoordUI("第七张彩票");
	private LotteryCoordUI lottery8 = new LotteryCoordUI("第八张彩票");
	private LotteryCoordUI lottery9 = new LotteryCoordUI("第九张彩票");
	private LotteryCoordUI lottery10 = new LotteryCoordUI("第十张彩票");
	private LotteryCoordUI lottery11 = new LotteryCoordUI("第十一张彩票");
	private LotteryCoordUI lottery12 = new LotteryCoordUI("第十二张彩票");

	private VersionCoordUI bottomVersion = new VersionCoordUI("底部版本号");
	private JPanel contentPanel = new JPanel();

	/** 按钮面板元素 */
	private JButton okButton = new JButton("确定");
	private JButton closeButton = new JButton("关闭");
	private JButton resetButton = new JButton("默认");
	private JPanel buttonPanel = new JPanel();

	/**
	 * 构造函数
	 * 
	 * @param mainUI
	 */
	public CoordItemUI(MainUI mainUI) {
		// 设置应用程序名称
		this.setTitle("设置打印坐标");
		// 使用 System exit 方法退出应用程序。仅在应用程序中使用。
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// this.setBounds((int)((SystemProperties.SCREEN_WIDTH-800)/2+150),
		// (int)((SystemProperties.SCREEN_HEIGHT-600)/2+150), 500, 300);
		this.setBounds((int) ((SystemProperties.SCREEN_WIDTH - 800) / 2 + 125),
				(int) ((SystemProperties.SCREEN_HEIGHT - 600) / 2) + 125, 600,
				350);

		initLayout();
		// 加载配置文件中的值到界面
		loadProperties();
	}

	private void initLayout() {
		// 设置布局管理器
		this.getContentPane().setLayout(new BorderLayout());

		// 内容面板
		this.contentPanel.setLayout(new BorderLayout());
		// 顶部版本号
		this.topVersion.setMinnumY(0);
		this.topVersion.setMaxnumY(40);
		this.contentPanel.add(topVersion, BorderLayout.NORTH);
		// 彩票
		JPanel lotteriesPanel = new JPanel();
		lotteriesPanel.setLayout(new GridLayout(3, 4));

		lotteriesPanel.add(lottery1);
		lotteriesPanel.add(lottery2);
		lotteriesPanel.add(lottery3);
		lotteriesPanel.add(lottery4);
		lotteriesPanel.add(lottery5);
		lotteriesPanel.add(lottery6);
		lotteriesPanel.add(lottery7);
		lotteriesPanel.add(lottery8);
		lotteriesPanel.add(lottery9);
		lotteriesPanel.add(lottery10);
		lotteriesPanel.add(lottery11);
		lotteriesPanel.add(lottery12);

		/*
		 * this.lottery1.setMinnumY(40); this.lottery1.setMaxnumY(140);
		 * lotteriesPanel.add(lottery1); this.lottery2.setMinnumY(40);
		 * this.lottery2.setMaxnumY(140); lotteriesPanel.add(lottery2);
		 * this.lottery3.setMinnumY(40); this.lottery3.setMaxnumY(140);
		 * lotteriesPanel.add(lottery3); this.lottery4.setMinnumY(150);
		 * this.lottery4.setMaxnumY(297); lotteriesPanel.add(lottery4);
		 * this.lottery5.setMinnumY(150); this.lottery5.setMaxnumY(297);
		 * lotteriesPanel.add(lottery5); this.lottery6.setMinnumY(150);
		 * this.lottery6.setMaxnumY(297); lotteriesPanel.add(lottery6);
		 */

		this.contentPanel.add(lotteriesPanel, BorderLayout.CENTER);
		// 底部版本号
		this.bottomVersion.setMinnumY(257);
		this.bottomVersion.setMaxnumY(297);
		this.contentPanel.add(bottomVersion, BorderLayout.SOUTH);
		// 将内容面板添加到父容器当中
		this.getContentPane().add(contentPanel, BorderLayout.NORTH);

		// 按钮面板
		this.okButton.addActionListener(this);
		this.buttonPanel.add(okButton);
		this.resetButton.addActionListener(this);
		this.buttonPanel.add(resetButton);
		this.closeButton.addActionListener(this);
		this.buttonPanel.add(closeButton);
		// 将按钮面板添加到父容器当中
		this.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		// this.pack();
	}

	@Override
	public void action() {
		this.setResizable(false);
		this.validate();
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();

		if (src == resetButton)
			resetButtonAction();
		else if (src == okButton)
			okButtonAction();
		else if (src == closeButton)
			this.dispose();
	}

	/**
	 * 加载配置文件中的值到界面
	 */
	private void loadProperties() {
		Properties properties = PropertiesUtils.createFromInputStream(getClass().getResourceAsStream(Constants.CONF_PROPERTIES_PRINT_COORD));

		// 加载配置文件中的数据
		topVersion.setXValue(PropertiesUtils.getPropertyAsFloat(properties, "topVersionX"));
		topVersion.setYValue(PropertiesUtils.getPropertyAsFloat(properties, "topVersionY"));

		lottery1.setXValue(PropertiesUtils.getPropertyAsFloat(properties, "lottery1X"));
		lottery1.setYValue(PropertiesUtils.getPropertyAsFloat(properties, "lottery1Y"));
		lottery2.setXValue(PropertiesUtils.getPropertyAsFloat(properties, "lottery2X"));
		lottery2.setYValue(PropertiesUtils.getPropertyAsFloat(properties, "lottery2Y"));
		lottery3.setXValue(PropertiesUtils.getPropertyAsFloat(properties, "lottery3X"));
		lottery3.setYValue(PropertiesUtils.getPropertyAsFloat(properties, "lottery3Y"));
		lottery4.setXValue(PropertiesUtils.getPropertyAsFloat(properties, "lottery4X"));
		lottery4.setYValue(PropertiesUtils.getPropertyAsFloat(properties, "lottery4Y"));
		lottery5.setXValue(PropertiesUtils.getPropertyAsFloat(properties, "lottery5X"));
		lottery5.setYValue(PropertiesUtils.getPropertyAsFloat(properties, "lottery5Y"));
		lottery6.setXValue(PropertiesUtils.getPropertyAsFloat(properties, "lottery6X"));
		lottery6.setYValue(PropertiesUtils.getPropertyAsFloat(properties, "lottery6Y"));
		lottery7.setXValue(PropertiesUtils.getPropertyAsFloat(properties, "lottery7X"));
		lottery7.setYValue(PropertiesUtils.getPropertyAsFloat(properties, "lottery7Y"));
		lottery8.setXValue(PropertiesUtils.getPropertyAsFloat(properties, "lottery8X"));
		lottery8.setYValue(PropertiesUtils.getPropertyAsFloat(properties, "lottery8Y"));
		lottery9.setXValue(PropertiesUtils.getPropertyAsFloat(properties, "lottery9X"));
		lottery9.setYValue(PropertiesUtils.getPropertyAsFloat(properties, "lottery9Y"));
		lottery10.setXValue(PropertiesUtils.getPropertyAsFloat(properties, "lottery10X"));
		lottery10.setYValue(PropertiesUtils.getPropertyAsFloat(properties, "lottery10Y"));
		lottery11.setXValue(PropertiesUtils.getPropertyAsFloat(properties, "lottery11X"));
		lottery11.setYValue(PropertiesUtils.getPropertyAsFloat(properties, "lottery11Y"));
		lottery12.setXValue(PropertiesUtils.getPropertyAsFloat(properties, "lottery12X"));
		lottery12.setYValue(PropertiesUtils.getPropertyAsFloat(properties, "lottery12Y"));
		
		bottomVersion.setXValue(PropertiesUtils.getPropertyAsFloat(properties, "bottomVersionX"));
		bottomVersion.setYValue(PropertiesUtils.getPropertyAsFloat(properties, "bottomVersionY"));
	}

	/**
	 * 确定按钮事件
	 */
	private void okButtonAction() {
		// 输入参数合法性检查
		if (!isValidParams())
			return;

		try {
			Properties properties = new Properties();
			properties.setProperty("topVersionX", topVersion.getXValue());
			properties.setProperty("topVersionY", topVersion.getYValue());

			properties.setProperty("lottery1X", lottery1.getXValue());
			properties.setProperty("lottery1Y", lottery1.getYValue());
			properties.setProperty("lottery2X", lottery2.getXValue());
			properties.setProperty("lottery2Y", lottery2.getYValue());
			properties.setProperty("lottery3X", lottery3.getXValue());
			properties.setProperty("lottery3Y", lottery3.getYValue());
			properties.setProperty("lottery4X", lottery4.getXValue());
			properties.setProperty("lottery4Y", lottery4.getYValue());
			properties.setProperty("lottery5X", lottery5.getXValue());
			properties.setProperty("lottery5Y", lottery5.getYValue());
			properties.setProperty("lottery6X", lottery6.getXValue());
			properties.setProperty("lottery6Y", lottery6.getYValue());

			properties.setProperty("lottery7X", lottery7.getXValue());
			properties.setProperty("lottery7Y", lottery7.getYValue());
			properties.setProperty("lottery8X", lottery8.getXValue());
			properties.setProperty("lottery8Y", lottery8.getYValue());
			properties.setProperty("lottery9X", lottery9.getXValue());
			properties.setProperty("lottery9Y", lottery9.getYValue());
			properties.setProperty("lottery10X", lottery10.getXValue());
			properties.setProperty("lottery10Y", lottery10.getYValue());
			properties.setProperty("lottery11X", lottery11.getXValue());
			properties.setProperty("lottery11Y", lottery11.getYValue());
			properties.setProperty("lottery12X", lottery12.getXValue());
			properties.setProperty("lottery12Y", lottery12.getYValue());

			properties.setProperty("bottomVersionX", bottomVersion.getXValue());
			properties.setProperty("bottomVersionY", bottomVersion.getYValue());

			PropertiesUtils.writeToFile(properties, getClass().getResource(Constants.CONF_PROPERTIES_PRINT_COORD).getFile());

			MessageUtils.info("                                设置成功！");
		} catch (Exception ex) {
			MessageUtils.alert("打印坐标设置失败！");
			System.out.println(ex.getMessage());
		}

	}

	/**
	 * 检查输入参数的合法性
	 * 
	 * @return
	 */
	public boolean isValidParams() {
		/*
		 * if(Double.parseDouble(topVersion.getXValue()) >
		 * topVersion.getMaxnumX() || Double.parseDouble(topVersion.getXValue())
		 * < topVersion.getMinnumX()) {
		 * MessageUtil.alert("顶部版号X坐标参数值范围必须在:"+topVersion
		 * .getMinnumX()+"-"+topVersion.getMaxnumX()+"之间。"); return false; }
		 * else if(Double.parseDouble(topVersion.getYValue()) >
		 * topVersion.getMaxnumY() || Double.parseDouble(topVersion.getYValue())
		 * < topVersion.getMinnumY()) {
		 * MessageUtil.alert("顶部版号Y坐标参数值范围必须在:"+topVersion
		 * .getMinnumX()+"-"+topVersion.getMaxnumX()+"之间。"); return false; }
		 * else if(Double.parseDouble(lottery1.getXValue()) <
		 * lottery1.getMinnumX()|| Double.parseDouble(lottery1.getXValue()) >
		 * lottery1.getMaxnumX()) {
		 * MessageUtil.alert("第一张彩票X坐标参数值范围必须在:"+lottery1
		 * .getMinnumX()+"-"+lottery1.getMaxnumX()+"之间。"); return false; } else
		 * if(Double.parseDouble(lottery1.getYValue()) < lottery1.getMinnumY()||
		 * Double.parseDouble(lottery1.getYValue()) > lottery1.getMaxnumY()) {
		 * MessageUtil
		 * .alert("第一张彩票Y坐标参数值范围必须在:"+lottery1.getMinnumY()+"-"+lottery1
		 * .getMaxnumY()+"之间。"); return false; } else
		 * if(Double.parseDouble(lottery2.getXValue()) < lottery2.getMinnumX()||
		 * Double.parseDouble(lottery2.getXValue()) > lottery2.getMaxnumX()) {
		 * MessageUtil
		 * .alert("第二张彩票X坐标参数值范围必须在:"+lottery2.getMinnumX()+"-"+lottery2
		 * .getMaxnumX()+"之间。"); return false; } else
		 * if(Double.parseDouble(lottery2.getYValue()) < lottery2.getMinnumY()||
		 * Double.parseDouble(lottery2.getYValue()) > lottery2.getMaxnumY()) {
		 * MessageUtil
		 * .alert("第二张彩票Y坐标参数值范围必须在:"+lottery2.getMinnumY()+"-"+lottery2
		 * .getMaxnumY()+"之间。"); return false; } else
		 * if(Double.parseDouble(lottery3.getXValue()) < lottery3.getMinnumX()||
		 * Double.parseDouble(lottery3.getXValue()) > lottery3.getMaxnumX()) {
		 * MessageUtil
		 * .alert("第三张彩票X坐标参数值范围必须在:"+lottery3.getMinnumX()+"-"+lottery3
		 * .getMaxnumX()+"之间。"); return false; } else
		 * if(Double.parseDouble(lottery3.getYValue()) < lottery3.getMinnumY()||
		 * Double.parseDouble(lottery3.getYValue()) > lottery3.getMaxnumY()) {
		 * MessageUtil
		 * .alert("第三张彩票Y坐标参数值范围必须在:"+lottery3.getMinnumY()+"-"+lottery3
		 * .getMaxnumY()+"之间。"); return false; } else
		 * if(Double.parseDouble(lottery4.getXValue()) < lottery4.getMinnumX()||
		 * Double.parseDouble(lottery4.getXValue()) > lottery4.getMaxnumX()) {
		 * MessageUtil
		 * .alert("第四张彩票X坐标参数值范围必须在:"+lottery4.getMinnumX()+"-"+lottery4
		 * .getMaxnumX()+"之间。"); return false; } else
		 * if(Double.parseDouble(lottery4.getYValue()) < lottery4.getMinnumY()||
		 * Double.parseDouble(lottery4.getYValue()) > lottery4.getMaxnumY()) {
		 * MessageUtil
		 * .alert("第四张彩票Y坐标参数值范围必须在:"+lottery4.getMinnumY()+"-"+lottery4
		 * .getMaxnumY()+"之间。"); return false; } else
		 * if(Double.parseDouble(lottery5.getXValue()) < lottery5.getMinnumX()||
		 * Double.parseDouble(lottery5.getXValue()) > lottery5.getMaxnumX()) {
		 * MessageUtil
		 * .alert("第五张彩票X坐标参数值范围必须在:"+lottery5.getMinnumX()+"-"+lottery5
		 * .getMaxnumX()+"之间。"); return false; } else
		 * if(Double.parseDouble(lottery5.getYValue()) < lottery5.getMinnumY()||
		 * Double.parseDouble(lottery5.getYValue()) > lottery5.getMaxnumY()) {
		 * MessageUtil
		 * .alert("第五张彩票Y坐标参数值范围必须在:"+lottery5.getMinnumY()+"-"+lottery5
		 * .getMaxnumY()+"之间。"); return false; } else
		 * if(Double.parseDouble(lottery6.getXValue()) < lottery6.getMinnumX()||
		 * Double.parseDouble(lottery6.getXValue()) > lottery6.getMaxnumX()) {
		 * MessageUtil
		 * .alert("第六张彩票X坐标参数值范围必须在:"+lottery6.getMinnumX()+"-"+lottery6
		 * .getMaxnumX()+"之间。"); return false; } else
		 * if(Double.parseDouble(lottery6.getYValue()) < lottery6.getMinnumY()||
		 * Double.parseDouble(lottery6.getYValue()) > lottery6.getMaxnumY()) {
		 * MessageUtil
		 * .alert("第六张彩票Y坐标参数值范围必须在:"+lottery6.getMinnumY()+"-"+lottery6
		 * .getMaxnumY()+"之间。"); return false; }
		 * if(Double.parseDouble(bottomVersion.getXValue()) >
		 * bottomVersion.getMaxnumX() ||
		 * Double.parseDouble(bottomVersion.getXValue()) <
		 * bottomVersion.getMinnumX()) {
		 * MessageUtil.alert("底部版号X坐标参数值范围必须在:"+bottomVersion
		 * .getMinnumX()+"-"+bottomVersion.getMaxnumX()+"之间。"); return false; }
		 * else if(Double.parseDouble(bottomVersion.getYValue()) >
		 * bottomVersion.getMaxnumY() ||
		 * Double.parseDouble(bottomVersion.getYValue()) <
		 * bottomVersion.getMinnumY()) {
		 * MessageUtil.alert("底部版号Y坐标参数值范围必须在:"+bottomVersion
		 * .getMinnumX()+"-"+bottomVersion.getMaxnumX()+"之间。"); return false; }
		 */

		return true;
	}

	/**
	 * 将坐标各值设为默认状态
	 */
	private void resetButtonAction() {
		// 版号坐标
		this.topVersion.setXValue(77.75f);
		this.topVersion.setYValue(15f);
		this.bottomVersion.setXValue(77.75f);
		this.bottomVersion.setYValue(280f);

		// 各彩票坐标
		this.lottery1.setXValue(34f);
		this.lottery1.setYValue(83.5f);
		this.lottery2.setXValue(100.5f);
		this.lottery2.setYValue(83.5f);
		this.lottery3.setXValue(167f);
		this.lottery3.setYValue(83.5f);
		this.lottery4.setXValue(233.5f);
		this.lottery4.setYValue(83.5f);

		this.lottery5.setXValue(34f);
		this.lottery5.setYValue(200.5f);
		this.lottery6.setXValue(100.5f);
		this.lottery6.setYValue(200.5f);
		this.lottery7.setXValue(167f);
		this.lottery7.setYValue(200.5f);
		this.lottery8.setXValue(233.5f);
		this.lottery8.setYValue(200.5f);

		this.lottery9.setXValue(34f);
		this.lottery9.setYValue(317.5f);
		this.lottery10.setXValue(100.5f);
		this.lottery10.setYValue(317.5f);
		this.lottery11.setXValue(167f);
		this.lottery11.setYValue(317.5f);
		this.lottery12.setXValue(233.5f);
		this.lottery12.setYValue(317.5f);

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

}
