package org.walkerljl.lotteryprinter.client.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.print.PrintService;

import org.walkerljl.lotteryprinter.client.common.PrintUtils;
import org.walkerljl.lotteryprinter.client.core.ScheduleCenter;
import org.walkerljl.lotteryprinter.client.core.ScheduleException;
import org.walkerljl.lotteryprinter.client.entity.Task;
import org.walkerljl.lotteryprinter.client.enums.ProducerState;
import org.walkerljl.lotteryprinter.client.enums.SystemState;
import org.walkerljl.lotteryprinter.client.service.LotteryPrinterService;

/**
 * 彩票打印
 * 
 * @author lijunlin
 */
public class LotteryPrinterServiceImpl implements LotteryPrinterService {
	/** Excel文件头 */
	private static String[] header = { "serial_number", "activation_code" };
	/** 调度员 */
	private ScheduleCenter scheduler;

	public LotteryPrinterServiceImpl() {
		this.scheduler = new ScheduleCenter();
	}

	@Override
	public void loadExcel(String filePath) throws ScheduleException {
		scheduler.loadExcel(filePath, header);
	}

	@Override
	public void startConsumeTask(Map<String, Object> printServices) {
		// 获取所有打印服务
		PrintService[] tempPrintServices = new PrintUtils().getAllPrintService();
		List<PrintService> printServiceList = new ArrayList<PrintService>();

		// 获取用户选择的打印服务对象
		for (PrintService printService : tempPrintServices) {
			if (printServices.containsKey(printService.getName())) {
				printServiceList.add(printService);
			}
		}

		// 处理任务
		scheduler.startConsumeTask(printServiceList);
	}

	@Override
	public void pause() {
		scheduler.pause();
	}

	@Override
	public boolean isRestOfTask() {
		// 当前任务队列不为空
		if (scheduler.getTaskQueue().size() > 0)
			return true;
		else {
			// 系统正在生产任务
			if (scheduler.getProducerState() == ProducerState.PRODUCING)
				return true;
		}

		return false;
	}

	@Override
	public SystemState getSystemState() {
		return scheduler.getSystemState();
	}

	@Override
	public Map<Long, Task> getBackupMap() {
		// return scheduler.getBackupMap();
		return null;
	}

	@Override
	public void startProduceTask() {
		scheduler.startProduceTask();
	}

	@Override
	public boolean isCanSwitch() {
		return scheduler.isCanSwitch();
	}

	@Override
	public void doSwitch() {
		scheduler.doSwitch();
	}

	@Override
	public void addTaskByIds(Set<Long> idSet) {
		scheduler.addTaskByIds(idSet);
	}

}
