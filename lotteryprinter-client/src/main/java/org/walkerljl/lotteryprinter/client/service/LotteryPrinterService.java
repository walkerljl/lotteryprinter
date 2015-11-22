package org.walkerljl.lotteryprinter.client.service;

import java.util.Map;
import java.util.Set;

import org.walkerljl.lotteryprinter.client.entity.Task;
import org.walkerljl.lotteryprinter.client.enums.SystemState;

/**
 * 彩票打印业务接口
 * 
 * @author lijunlin
 */
public interface LotteryPrinterService {

	/**
	 * 加载Excel数据文件
	 * 
	 * @param filePath
	 *            文件路径
	 * @return
	 */
	void loadExcel(String filePath);

	/**
	 * 开始生产任务
	 */
	void startProduceTask();

	/**
	 * 处理任务
	 * 
	 * @param printServices
	 *            打印服务对象
	 */
	void startConsumeTask(Map<String, Object> printServices);

	/**
	 * 暂停正在进行的打印
	 */
	void pause();

	/**
	 * 根据指定任务编号添加任务
	 * 
	 * @param idSet
	 */
	void addTaskByIds(Set<Long> idSet);

	/**
	 * 是否还有任务
	 * 
	 * @return
	 */
	boolean isRestOfTask();

	/**
	 * 获取系统运行状态
	 * 
	 * @return
	 */
	SystemState getSystemState();

	/**
	 * 获取任务备份
	 * 
	 * @return
	 */
	Map<Long, Task> getBackupMap();

	/**
	 * 查看能否切换
	 * 
	 * @return
	 */
	boolean isCanSwitch();

	/**
	 * 切换
	 */
	void doSwitch();

}
