package org.walkerljl.lotteryprinter.client.core.runnable;

import java.util.Map;

import org.walkerljl.lotteryprinter.client.common.LogUtils;
import org.walkerljl.lotteryprinter.client.core.ScheduleCenter;
import org.walkerljl.lotteryprinter.client.entity.Task;
import org.walkerljl.lotteryprinter.client.enums.ConsumerState;
import org.walkerljl.lotteryprinter.client.enums.ProducerState;

/**
 * 生产者
 * 
 * @author lijunlin
 */
public class ProduceTaskJob implements Runnable {
	/** 调度员 */
	private ScheduleCenter scheduler;
	/** 日志 */
	private LogUtils logger = LogUtils.getInstance();

	/**
	 * 构造函数
	 * 
	 * @param scheduler
	 * @param path
	 * @param header
	 */
	public ProduceTaskJob(ScheduleCenter scheduler) {
		this.scheduler = scheduler;
		// 设置线程名称
		Thread.currentThread().setName("Producer");
	}

	@Override
	public void run() {
		try {
			// 没有可用生成任务的数据
			if (scheduler.getBackupMap().size() <= 0
					|| scheduler.getSuffixOfSheet().intValue() >= scheduler
							.getBackupMap().size()) {
				logger.error("当前没有可用的数据，请导入相关数据再点击此操作！");
				return;
			}

			// 获取当前suffixOfSheet并自增
			int currentSuffixOfSheet = scheduler.getSuffixOfSheet()
					.incrementAndGet();
			// 获取当前sheet中的所有数据
			Map<Long, Task> currentTasksOfSheet = scheduler.getBackupMap().get(
					currentSuffixOfSheet);

			/*
			 * 计算已经处理的最后一个任务的id
			 */
			// 已经处理任务个数
			long handledTaskCount = 0l;
			int suffix = 1;
			while (suffix < currentSuffixOfSheet) {
				handledTaskCount += scheduler.getBackupMap().get(suffix).size();
				// 游标自增
				suffix++;
			}
			// 已经处理的最后一个任务的id
			long handledFinalTaskId = 1000000000l + handledTaskCount;
			// 待处理最后一个人任务的id
			long neddHandledFinalTaskId = handledFinalTaskId
					+ currentTasksOfSheet.size();

			while (handledFinalTaskId < neddHandledFinalTaskId) {
				Task task = currentTasksOfSheet.get(++handledFinalTaskId);
				scheduler.getTaskQueue().put(task);
				logger.info("[Producer]添加任务[" + task.getId() + "]。");
			}

			// 检查是否需要修改标志位
			if (currentSuffixOfSheet < scheduler.getBackupMap().size()) {
				// 运行状态
				scheduler.setProducerState(ProducerState.PRODUCED_ONE_SHEET);
				int countOfRest = scheduler.getBackupMap().size()
						- currentSuffixOfSheet;
				logger.info("[Producer]已经处理完第[" + currentSuffixOfSheet
						+ "]个sheet中的数据，剩余sheet为[" + countOfRest + "]。\n");
				// 切换标志
				if (scheduler.getTaskQueue().size() <= 0) {
					if (scheduler.getConsumerState() == ConsumerState.CONSUMERED_ONE_SHEET)
						scheduler.setCanSwitch(true);
				}
			} else {
				scheduler.setProducerState(ProducerState.PRODUCED_ALL_SHEET);
				logger.info("[Producer]已经处理完所有sheet中的数据。\n");
			}

		} catch (Exception ex) {
			// 出现异常时修改标志位为ProducerState.INITIALIZATION
			scheduler.setProducerState(ProducerState.INITIALIZATION);
			logger.error("[Producer]服务异常终止，详细信息如下：");
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}
	}

}
