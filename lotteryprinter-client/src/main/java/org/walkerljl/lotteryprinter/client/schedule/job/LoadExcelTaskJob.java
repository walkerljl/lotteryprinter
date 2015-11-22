package org.walkerljl.lotteryprinter.client.schedule.job;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.walkerljl.lotteryprinter.client.common.ExcelUtilException;
import org.walkerljl.lotteryprinter.client.common.ExcelUtils;
import org.walkerljl.lotteryprinter.client.common.LoggerUtils;
import org.walkerljl.lotteryprinter.client.entity.Lottery;
import org.walkerljl.lotteryprinter.client.entity.Task;
import org.walkerljl.lotteryprinter.client.schedule.Scheduler;

/**
 * 加载数据
 * 
 * @author lijunlin
 */
public class LoadExcelTaskJob implements Runnable {
	/** 调度员 */
	private Scheduler scheduler;
	/** Excel文件路径 */
	private String filePath;
	/** Excel文件头 */
	private String[] header;
	/** 每版打印彩票数量 */
	private int countOfPage = Scheduler.COUNT_OF_PAGE;
	/** 日志 */
	private LoggerUtils logger = LoggerUtils.getInstance();

	/**
	 * 构造函数
	 * 
	 * @param scheduler
	 * @param filePath
	 * @param header
	 */
	public LoadExcelTaskJob(Scheduler scheduler, String filePath,
			String[] header) {
		this.scheduler = scheduler;
		this.filePath = filePath;
		this.header = header;
	}

	@Override
	public void run() {
		try {
			// 加载Excel文件数据, 计算大约将产生多少个任务, 并显示提示信息
			logger.info("正在尝试将Excel文件中的数据加载到系统，请稍等......");
			long startTime = System.currentTimeMillis();
			// Excel数据集合
			List<List<Object[]>> excelDataList = ExcelUtils.readExcel(filePath,
					header);

			// 设置版号
			long id = 1000000000l;
			// 设置sheet号
			int suffixOfSheet = 0;

			for (List<Object[]> dataOfSheet : excelDataList) {
				// 为每个sheet重新定义一个阻塞Map
				Map<Long, Task> mapOfSheet = new ConcurrentHashMap<Long, Task>();
				// 定义sheet单元的行号
				long lineNumber = 1l;
				// 初始化数组，设置数组游标
				Lottery[] lotteries = new Lottery[countOfPage];
				int suffix = 0;

				for (Object[] dataOfRow : dataOfSheet) {
					// 行号自增
					lineNumber++;
					// 获取彩票内容
					String serialNumber = handleCell(dataOfRow[0]);
					if (serialNumber.equals(""))
						continue;
					String passCode = handleCell(dataOfRow[1]);
					if (passCode.equals(""))
						continue;

					// 数组元素依次赋值
					lotteries[suffix] = new Lottery(serialNumber, passCode);

					// 判断数组下标是否为ScheduleCenter.COUNT_OF_PAGE - 1
					if (suffix == countOfPage - 1) {
						// 创建一份任务
						Task task = packOneTask(new Task(++id, lotteries),
								lineNumber);
						// 添加任务
						mapOfSheet.put(task.getId(), task);
						// 游标置零
						suffix = 0;
						// 清空Lottery数组里面的数据
						lotteries = new Lottery[countOfPage];
					} else {
						// 游标自增
						suffix++;
					}
				}

				// 如果剩下的彩票张数不足一版，则将这些彩票也放入一个任务
				if (lotteries[0] != null) {
					// 判断到底数组当中有多少条数据
					int size = 0;
					for (; size < lotteries.length; size++) {
						if (lotteries[size] == null)
							break;
					}

					// 创建一份任务
					Task task = new Task(++id, lotteries);
					task.setStartLineNumber(lineNumber - size + 2);
					task.setEndLineNumber(lineNumber);
					// 添加任务
					mapOfSheet.put(task.getId(), task);
				}

				// 完成一个sheet
				scheduler.getBackupMap().put(++suffixOfSheet, mapOfSheet);

			}

			// 预处理完成，显示相关提示信息
			logger.info("Excel预处理完成，有效任务累计个数["
					+ getCountOfTotalTask(scheduler.getBackupMap()) + "]。"
					+ "共耗时[" + (System.currentTimeMillis() - startTime) + "]。");

		} catch (ExcelUtilException ex) {
			logger.error("服务[LoadExcelTarget]异常终止！详细信息如下：\n" + ex.getMessage());
		} catch (Exception ex) {
			logger.error("服务[LoadExcelTarget]异常终止！详细信息如下：\n" + ex.getMessage());
		}
	}

	/**
	 * 获取总的任务个数
	 * 
	 * @param map
	 * @return
	 */
	private long getCountOfTotalTask(Map<Integer, Map<Long, Task>> map) {
		// 总的任务个数
		long countOfTask = 0l;
		for (Map.Entry<Integer, Map<Long, Task>> entry : map.entrySet()) {
			countOfTask += entry.getValue().size();
		}

		return countOfTask;
	}

	/**
	 * 打包任务
	 * 
	 * @param task
	 * @return
	 */
	private Task packOneTask(Task task, long lineNumber) {
		// 设置任务对应起始行号
		task.setStartLineNumber(lineNumber - countOfPage + 1);
		task.setEndLineNumber(lineNumber);

		return task;
	}

	/**
	 * 处理Excel单元
	 * 
	 * @param data
	 * @return
	 */
	private String handleCell(Object data) {
		// 判断是否为null
		if (data == null) {
			return "";
		}

		// 类型转换，处理科学计数法的问题
		DecimalFormat df = new DecimalFormat("0");
		String temp = df.format(Double.parseDouble(data.toString().trim()));

		StringBuilder result = new StringBuilder();
		// 业务需求处理:每四个数字中间两个空格
		for (int i = 0; i < temp.length() / 4; i++) {
			result.append(temp.substring(i * 4, (i + 1) * 4)).append("  ");
		}

		// 去除最后两个空格,并返回
		return result.toString().substring(0, result.length() - 2);
	}

}
