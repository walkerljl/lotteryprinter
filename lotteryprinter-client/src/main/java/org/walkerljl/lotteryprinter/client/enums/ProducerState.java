package org.walkerljl.lotteryprinter.client.enums;

/**
 * 生产者运行状态
 * 
 * @author lijunlin
 */
public enum ProducerState {
	/**
	 * 初始化状态
	 */
	INITIALIZATION,
	/**
	 * 正在生产
	 */
	PRODUCING,
	/**
	 * 生产完一个Sheet数据
	 */
	PRODUCED_ONE_SHEET,
	/**
	 * 生产完所有Sheet数据
	 */
	PRODUCED_ALL_SHEET,
}
