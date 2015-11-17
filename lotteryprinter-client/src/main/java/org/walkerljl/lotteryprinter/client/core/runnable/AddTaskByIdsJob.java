package org.walkerljl.lotteryprinter.client.core.runnable;

import java.util.Map;
import java.util.Set;

import org.walkerljl.lotteryprinter.client.common.LogUtils;
import org.walkerljl.lotteryprinter.client.core.ScheduleCenter;
import org.walkerljl.lotteryprinter.client.entity.Task;
import org.walkerljl.lotteryprinter.client.enums.ProducerState;
import org.walkerljl.lotteryprinter.client.enums.SystemState;

/**
 * 根据任务编号添加任务到打印队列
 * 
 * @author lijunlin
 */
public class AddTaskByIdsJob implements Runnable {
	/** 调度员 */
	private ScheduleCenter scheduler;
	/** 需要重新打印的任务编号集合 */
	private Set<Long> idSet;
	/** 日志 */
	private LogUtils logger = LogUtils.getInstance();

	/**
	 * 构造函数
	 * 
	 * @param scheduler
	 * @param idSet
	 */
	public AddTaskByIdsJob(ScheduleCenter scheduler, Set<Long> idSet) {
		this.scheduler = scheduler;
		this.idSet = idSet;
	}

	@Override
	public void run() {
		try {
			for (Long id : idSet) {
				Task task = null;
				// 遍历
				for (Map.Entry<Integer, Map<Long, Task>> entry : scheduler
						.getBackupMap().entrySet()) {
					task = entry.getValue().get(id);
					// 如果查找到对应任务，结束循环
					if (task != null)
						break;
				}

				if (task != null) {
					try {
						scheduler.getTaskQueue().put(task);
						logger.info("编号为[" + task.getId()
								+ "]的任务尝试添加到任务队列, 成功！");
					} catch (Exception ex) {
						logger.info("编号为[" + task.getId()
								+ "]的任务尝试添加到任务队列, 失败！");
					}
				} else {
					logger.error("不存在编号为[" + id + "]的任务！");
				}
			}

		} catch (Exception ex) {
			logger.error("服务[AddTaskByIds]异常终止！");
		} finally {
			// 修改标志位
			scheduler.setSystemState(SystemState.INITIALIZATION);
			scheduler.setProducerState(ProducerState.INITIALIZATION);
		}

	}

}
