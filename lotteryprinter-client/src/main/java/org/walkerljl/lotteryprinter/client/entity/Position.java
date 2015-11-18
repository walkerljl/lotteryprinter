package org.walkerljl.lotteryprinter.client.entity;

import java.io.Serializable;

/**
 * 打印坐标的相关信息
 * 
 * 此处坐标单位为毫米(mm)
 * 
 * @author lijunlin
 */
public class Position implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/** X坐标 */
	private float x;
	/** Y坐标 */
	private float y;

	/** 1mm为多少个点 */
	private static float pointsOfMM = 0;

	static {
		pointsOfMM = 2.83F;
	}

	public Position(String x, String y) {
		this.x = Float.parseFloat(x) * pointsOfMM;
		this.y = Float.parseFloat(y) * pointsOfMM;
	}

	/** 坐标加法 */
	public void add(float x, float y) {
		this.x += x * pointsOfMM;
		this.y += y * pointsOfMM;
	}

	/** 坐标减法 */
	public void minus(float x, float y) {
		this.x -= x * pointsOfMM;
		this.y -= y * pointsOfMM;
		if (this.x < 0)
			this.x = 0;
		if (this.y < 0)
			this.y = 0;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}
}
