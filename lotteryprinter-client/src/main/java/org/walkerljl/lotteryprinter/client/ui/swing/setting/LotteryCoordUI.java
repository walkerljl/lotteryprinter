package org.walkerljl.lotteryprinter.client.ui.swing.setting;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

/**
 * 
 * LotteryCoordUI
 *
 * @author lijunlin
 */
public class LotteryCoordUI extends JPanel {
	private static final long serialVersionUID = 1L;

	/** 名称 */
	private JLabel name;
	/** X坐标 */
	private JSpinner coordX;
	/** X最小值 */
	private double minnumX = 0;
	/** X最大值 */
	private double maxnumX = 210;
	/** Y坐标 */
	private JSpinner coordY;
	/** Y最小值 */
	private double minnumY = 0;
	/** Y最大值 */
	private double maxnumY = 297;
	/** 增长值 */
	private double stepSize = 0.1;

	/**
	 * 构造函数
	 */
	public LotteryCoordUI(String name) {
		this.name = new JLabel(name);

		initLayout();
		this.validate();
		this.setVisible(true);
	}

	/**
	 * 初始化元素布局
	 */
	public void initLayout() {
		this.coordX = new JSpinner(new SpinnerNumberModel(minnumX, minnumX,
				maxnumX, stepSize));
		this.coordY = new JSpinner(new SpinnerNumberModel(minnumY, minnumY,
				maxnumY, stepSize));

		this.setLayout(new GridLayout(2, 2));
		// 第一行第一列
		this.add(name);
		// 第一行第二列
		JPanel firstPanel = new JPanel();
		firstPanel.add(new JLabel("X:"));
		firstPanel.add(coordX);
		this.add(firstPanel);
		// 第二行第一列
		this.add(new JLabel());
		// 第二行第二列
		JPanel secondPanel = new JPanel();
		secondPanel.add(new JLabel("Y:"));
		secondPanel.add(coordY);
		this.add(secondPanel);
	}

	/**
	 * 获取X坐标的值
	 * 
	 * @return
	 */
	public String getXValue() {
		return this.coordX.getValue().toString();
	}

	/**
	 * 设置X坐标的值
	 * 
	 * @param value
	 */
	public void setXValue(Object value) {
		this.coordX.setValue(value);
		// 赋值之后根据当前数值调整控件大小
		this.coordX.updateUI();
	}

	/**
	 * 获取Y坐标的值
	 * 
	 * @return
	 */
	public String getYValue() {
		return this.coordY.getValue().toString();
	}

	/**
	 * 设置Y坐标的值
	 * 
	 * @param value
	 */
	public void setYValue(Object value) {
		this.coordY.setValue(value);
		// 赋值之后根据当前数值调整控件大小
		this.coordY.updateUI();
	}

	public double getMinnumX() {
		return minnumX;
	}

	public void setMinnumX(double minnumX) {
		this.minnumX = minnumX;
	}

	public double getMaxnumX() {
		return maxnumX;
	}

	public void setMaxnumX(double maxnumX) {
		this.maxnumX = maxnumX;
	}

	public double getMinnumY() {
		return minnumY;
	}

	public void setMinnumY(double minnumY) {
		this.minnumY = minnumY;
	}

	public double getMaxnumY() {
		return maxnumY;
	}

	public void setMaxnumY(double maxnumY) {
		this.maxnumY = maxnumY;
	}

	public double getStepSize() {
		return stepSize;
	}

	public void setStepSize(double stepSize) {
		this.stepSize = stepSize;
	}
}