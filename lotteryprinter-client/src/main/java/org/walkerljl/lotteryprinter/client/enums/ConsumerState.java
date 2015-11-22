package org.walkerljl.lotteryprinter.client.enums;

/**
 * 消费者运行状态
 * 
 * @author lijunlin
 */
public enum ConsumerState {
	/**
	 * 初始化状态
	 */
	INITIALIZATION,
	/**
	 * 正在处理
	 */
	CONSUMNERING,
	/**
	 * 处理完一个Sheet数据
	 */
	CONSUMERED_ONE_SHEET,
	/**
	 * 处理完所有Sheet数据
	 */
	CONSUMERED_ALL_SHEET,
}
