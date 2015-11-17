package org.walkerljl.lotteryprinter.client.entity;

/**
 * 彩票实体
 * 
 * @author lijunlin
 */
public class Lottery {
	/** 流水号 */
	private String serialNumber;
	/** 密码 */
	private String passCode;

	public Lottery() {
	}

	public Lottery(String serialNumber, String passCode) {
		this.serialNumber = serialNumber;
		this.passCode = passCode;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getPassCode() {
		return passCode;
	}

	public void setPassCode(String passCode) {
		this.passCode = passCode;
	}
}
