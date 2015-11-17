package org.walkerljl.lotteryprinter.client.entity;

/**
 * 任务
 * 
 * @author lijunlin
 */
public class Task {
	/** 编号 */
	private Long id;
	/** 彩票数组 */
	private Lottery[] lotteries;
	/** 开始行号 */
	private Long startLineNumber;
	/** 结束行号 */
	private Long endLineNumber;

	/**
	 * 构造函数
	 */
	public Task() {
	}

	/**
	 * 构造函数
	 * 
	 * @param id
	 *            编号
	 * @param lotteries
	 *            彩票数组
	 */
	public Task(Long id, Lottery[] lotteries) {
		this.id = id;
		this.lotteries = lotteries;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Lottery[] getLotteries() {
		return lotteries;
	}

	public void setLotteries(Lottery[] lotteries) {
		this.lotteries = lotteries;
	}

	public Long getStartLineNumber() {
		return startLineNumber;
	}

	public void setStartLineNumber(Long startLineNumber) {
		this.startLineNumber = startLineNumber;
	}

	public Long getEndLineNumber() {
		return endLineNumber;
	}

	public void setEndLineNumber(Long endLineNumber) {
		this.endLineNumber = endLineNumber;
	}
}
